package io.surati.gap.maccount.module.domain.db;

import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.maccount.module.domain.api.AnnualWarrant;
import io.surati.gap.maccount.module.domain.api.WarrantsToBundle;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaAnnualWarrantView;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
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
            .orderBy(MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.ID.desc())
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
}
