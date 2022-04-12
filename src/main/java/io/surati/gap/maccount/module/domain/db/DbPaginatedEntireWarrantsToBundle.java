package io.surati.gap.maccount.module.domain.db;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.AnnualWarrant;
import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.gtp.base.db.DbAnnualWarrant;
import io.surati.gap.gtp.base.db.DbBundle;
import io.surati.gap.gtp.base.db.DbSection;
import io.surati.gap.gtp.base.db.DbTitle;
import io.surati.gap.maccount.module.domain.api.PropBundleThreshold;
import io.surati.gap.maccount.module.domain.api.WarrantsToBundle;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaSubBundle;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaWarrantBundled;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaWarrantToBundleView;
import java.time.LocalDateTime;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.Cursor;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.impl.DSL;

public final class DbPaginatedEntireWarrantsToBundle implements WarrantsToBundle {

    private final DataSource src;

    private final Title title;

    private final Section section;

    private final Bundle pbundle;

    private final short year;

    private final String filter;

    private final Long nbperpage;

    private final Long page;

    /**
     * jOOQ database context.
     */
    private final DSLContext ctx;

    /**
     *
     * @param src Data source
     * @param title Title
     * @param section Section
     * @param pbundle Parent bundle
     * @param year Year
     * @param filter Filter on reference and imputation
     */
    public DbPaginatedEntireWarrantsToBundle(
        final DataSource src, final Long nbperpage, final Long page, final Title title,
        final Section section, final Bundle pbundle, final short year, final String filter
    ) {
        this.src = src;
        this.ctx = new JooqContext(this.src);
        this.nbperpage = nbperpage;
        this.page = page;
        this.title = title;
        this.section = section;
        this.pbundle = pbundle;
        this.year = year;
        this.filter = filter;
    }

    @Override
    public Iterable<AnnualWarrant> iterate() {
        return this.ctx
            .selectFrom(MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW)
            .where(this.condition())
            .orderBy(
                MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.DATE.asc(),
                MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.ID.asc()
            )
            .limit(this.nbperpage)
            .offset(this.nbperpage * (this.page - 1))
            .fetch(
                rec -> new DbAnnualWarrant(
                    this.src, rec.getId(), rec.getFiscalYear()
                )
            );
    }

    @Override
    public Long count() {
        return Long.valueOf(
            this.ctx
                .fetchCount(MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW, this.condition())
        );
    }

    /**
     * Bundle selected warrants.
     * @param warrants Warrants
     * @param author Author
     */
    private void bundle(
        final Iterable<AnnualWarrant> warrants,
        final Title atitle, final Section asection,
        final Bundle abundle, final User author
    ) {
        final Long sbdleid = this.ctx.insertInto(MaSubBundle.MA_SUB_BUNDLE)
            .set(MaSubBundle.MA_SUB_BUNDLE.FISCAL_YEAR, this.year)
            .set(MaSubBundle.MA_SUB_BUNDLE.CREATION_DATE, LocalDateTime.now())
            .set(MaSubBundle.MA_SUB_BUNDLE.AUTHOR_ID, author.id())
            .set(MaSubBundle.MA_SUB_BUNDLE.BUNDLE, abundle.code())
            .set(MaSubBundle.MA_SUB_BUNDLE.SECTION, asection.code())
            .set(MaSubBundle.MA_SUB_BUNDLE.TITLE, atitle.code())
            .set(MaSubBundle.MA_SUB_BUNDLE.BUNDLE_SPLIT_WARRANT, false)
            .set(
                MaSubBundle.MA_SUB_BUNDLE.NO,
                this.ctx.fetchCount(
                    MaSubBundle.MA_SUB_BUNDLE,
                    MaSubBundle.MA_SUB_BUNDLE.FISCAL_YEAR.eq(this.year)
                        .and(
                            MaSubBundle.MA_SUB_BUNDLE.BUNDLE_SPLIT_WARRANT.eq(false)
                        )
                ) + 1
            )
            .returning(MaSubBundle.MA_SUB_BUNDLE.ID)
            .fetchOne()
            .getId();
        for (final AnnualWarrant wr : warrants) {
            this.ctx.insertInto(MaWarrantBundled.MA_WARRANT_BUNDLED)
                .set(MaWarrantBundled.MA_WARRANT_BUNDLED.ID, wr.id())
                .set(MaWarrantBundled.MA_WARRANT_BUNDLED.FISCAL_YEAR, wr.year())
                .set(MaWarrantBundled.MA_WARRANT_BUNDLED.SUB_BUNDLE_ID, sbdleid)
                .execute();
        }
    }

    /**
     * Bundle warrants.
     * @param author Author
     * @param anyway Bundle even if threshold isn't be reached
     */
    private void bundle(final User author, final boolean anyway) {
        final int threshold = new PropBundleThreshold().totalPayment();
        try(
            Cursor<Record4<String, String, String, Integer>> cursor = this.ctx.select(
                    MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.TITLE,
                    MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.SECTION,
                    MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.BUNDLE,
                    DSL.count()
                ).from(MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW)
                .where(this.condition())
                .groupBy(
                    MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.TITLE,
                    MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.SECTION,
                    MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.BUNDLE
                ).fetchSize(1)
                .fetchLazy()
        ) {
            while (cursor.hasNext()) {
                final Record4<String, String, String, Integer> group = cursor.fetchNext();
                int nbpage = group.value4() / threshold;
                if (nbpage == 0) {
                    if (anyway) {
                        nbpage = 1;
                    } else {
                        continue;
                    }
                } else if (anyway && group.value4() % threshold > 0) {
                    nbpage += 1;
                }
                final Title ltitle = new DbTitle(this.src, group.value1());
                final Section lsection = new DbSection(this.src, group.value2());
                final Bundle lbundle = new DbBundle(this.src, group.value3());
                for (int pge = 1; pge <= nbpage; pge++) {
                    final Iterable<AnnualWarrant> warrants = new DbPaginatedEntireWarrantsToBundle(
                        this.src, (long)threshold, 1L,
                        ltitle,
                        lsection,
                        lbundle,
                        this.year,
                        this.filter
                    ).iterate();
                    this.bundle(warrants, ltitle, lsection, lbundle, author);
                }
            }
        }
    }

    @Override
    public void bundle(final User author) {
        this.bundle(author, false);
    }

    @Override
    public void bundleAnyway(final User author) {
        this.bundle(author, true);
    }

    @Override
    public AnnualWarrant get(final Long id) {
        if (!this.has(id)) {
            throw new IllegalArgumentException(
                String.format("Mandat annuel (ID=%s) de l'année %s pas trouvé !", id, this.year)
            );
        }
        return new DbAnnualWarrant(
            this.src,
            id, this.year
        );
    }

    /**
     * Has a warrant with identifier.
     * @param id Identifier
     * @return Has or not
     */
    @Override
    public boolean has(final Long id) {
        return this.ctx.fetchCount(
            MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW, this.condition(),
            MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.ID.eq(id)
        ) > 0;
    }

    private Condition condition() {
        Condition result = DSL.trueCondition();
        if (StringUtils.isNotBlank(this.filter)) {
            result = result.and(
                DSL.falseCondition()
                    .or(MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.REFERENCE.like("%" + this.filter + "%"))
                    .or(MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.IMPUTATION.like("%" + this.filter + "%"))
            );
        }
        result = result.and(
            MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.FISCAL_YEAR.eq(this.year)
        ).and(
            MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.IS_SPLIT.eq(false)
        );
        if (this.pbundle != Bundle.EMPTY) {
            result = result.and(
                MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.BUNDLE.eq(this.pbundle.code())
            );
        }
        if (this.section != Section.EMPTY) {
            result = result.and(
                MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.SECTION.eq(this.section.code())
            );
        }
        if (this.title != Title.EMPTY) {
            result = result.and(
                MaWarrantToBundleView.MA_WARRANT_TO_BUNDLE_VIEW.TITLE.eq(this.title.code())
            );
        }
        return result;
    }

}
