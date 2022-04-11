package io.surati.gap.maccount.module.domain.db;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.gtp.base.db.DbBundle;
import io.surati.gap.gtp.base.db.DbSection;
import io.surati.gap.gtp.base.db.DbTitle;
import io.surati.gap.maccount.module.domain.api.AnnualWarrant;
import io.surati.gap.maccount.module.domain.api.PropBundleThreshold;
import io.surati.gap.maccount.module.domain.api.WarrantsToBundle;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaAnnualWarrant;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaAnnualWarrantView;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaSubBundle;
import java.time.LocalDateTime;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.cactoos.list.ListOf;
import org.jooq.Condition;
import org.jooq.Cursor;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.Result;
import org.jooq.impl.DSL;

public final class DbPaginatedPartialWarrantsToBundle implements WarrantsToBundle {

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
    public DbPaginatedPartialWarrantsToBundle(
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
            .selectFrom(MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW)
            .where(this.condition())
            .orderBy(
                MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.DATE.asc(),
                MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.ID.asc()
            )
            .offset(this.nbperpage * (this.page - 1))
            .limit(this.nbperpage)
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
                .fetchCount(MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW, this.condition())
        );
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
            MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW, this.condition(),
            MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.ID.eq(id)
        ) > 0;
    }

    private Condition condition() {
        Condition result = DSL.trueCondition();
        if (StringUtils.isNotBlank(this.filter)) {
            result = result.and(
                DSL.falseCondition()
                    .or(MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.REFERENCE.like("%" + this.filter + "%"))
                    .or(MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.IMPUTATION.like("%" + this.filter + "%"))
            );
        }
        result = result.and(
            MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.FISCAL_YEAR.eq(this.year)
        ).and(
            MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.IS_SPLIT.eq(true)
        ).and(
            MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.SUB_BUNDLE_ID.isNull()
        );
        if (this.pbundle != Bundle.EMPTY) {
            result = result.and(
                MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.BUNDLE.eq(this.pbundle.code())
            );
        }
        if (this.section != Section.EMPTY) {
            result = result.and(
                MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.SECTION.eq(this.section.code())
            );
        }
        if (this.title != Title.EMPTY) {
            result = result.and(
                MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.TITLE.eq(this.title.code())
            );
        }
        return result;
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
            .set(MaSubBundle.MA_SUB_BUNDLE.BUNDLE_SPLIT_WARRANT, true)
            .set(
                MaSubBundle.MA_SUB_BUNDLE.NO,
                this.ctx.fetchCount(
                    MaSubBundle.MA_SUB_BUNDLE,
                    MaSubBundle.MA_SUB_BUNDLE.FISCAL_YEAR.eq(this.year)
                        .and(
                            MaSubBundle.MA_SUB_BUNDLE.BUNDLE_SPLIT_WARRANT.eq(true)
                        )
                ) + 1
            )
            .returning(MaSubBundle.MA_SUB_BUNDLE.ID)
            .fetchOne()
            .getId();
        for (AnnualWarrant wr : warrants) {
            this.ctx.update(MaAnnualWarrant.MA_ANNUAL_WARRANT)
                .set(MaAnnualWarrant.MA_ANNUAL_WARRANT.SUB_BUNDLE_ID, sbdleid)
                .where(
                    MaAnnualWarrant.MA_ANNUAL_WARRANT.WARRANT_ID.eq(wr.id())
                        .and(
                            MaAnnualWarrant.MA_ANNUAL_WARRANT.FISCAL_YEAR.eq(this.year)
                        )
                )
                .execute();
        }
    }

    /**
     * Bundle warrants.
     * @param author Author
     * @param anyway Bundle even if threshold isn't be reached
     */
    private void bundle(final User author, final boolean anyway) {
        final int threshold = new PropBundleThreshold().partialPayment();
        try(
            Cursor<Record4<String, String, String, Integer>> cursor = this.ctx.select(
                    MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.TITLE,
                    MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.SECTION,
                    MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.BUNDLE,
                    DSL.count()
                ).from(MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW)
                .where(this.condition())
                .groupBy(
                    MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.TITLE,
                    MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.SECTION,
                    MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.BUNDLE
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
                    final Iterable<AnnualWarrant> warrants = new DbPaginatedPartialWarrantsToBundle(
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
}
