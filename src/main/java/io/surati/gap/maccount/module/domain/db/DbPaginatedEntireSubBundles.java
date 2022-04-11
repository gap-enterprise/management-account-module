package io.surati.gap.maccount.module.domain.db;

import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.maccount.module.domain.api.SubBundle;
import io.surati.gap.maccount.module.domain.api.SubBundles;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaSubBundle;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public final class DbPaginatedEntireSubBundles implements SubBundles {

    private final DataSource src;

    private final Title title;

    private final Section section;

    private final Bundle bundle;

    private final short year;

    private final String filter;

    private final Long nbperpage;

    private final Long page;

    /**
     * jOOQ database context.
     */
    private final DSLContext ctx;

    public DbPaginatedEntireSubBundles(
        final DataSource src, final Long nbperpage, final Long page,
        final Title title, final Section section, final Bundle bundle,
        final short year, final String filter
    ) {
        this.src = src;
        this.ctx = new JooqContext(this.src);
        this.nbperpage = nbperpage;
        this.page = page;
        this.title = title;
        this.section = section;
        this.bundle = bundle;
        this.year = year;
        this.filter = filter;
    }

    @Override
    public SubBundle get(Long id) {
        if (!this.has(id)) {
            throw new IllegalArgumentException(
                String.format("Sous-liasse (ID=%s) de l'année %s pas trouvé !", id, this.year)
            );
        }
        return new DbSubBundle(
            this.src, id
        );
    }

    @Override
    public boolean has(final Long id) {
        return this.ctx.fetchCount(
            MaSubBundle.MA_SUB_BUNDLE, this.condition(),
            MaSubBundle.MA_SUB_BUNDLE.ID.eq(id)
        ) > 0;
    }

    @Override
    public Iterable<SubBundle> iterate() {
        return this.ctx
            .selectFrom(MaSubBundle.MA_SUB_BUNDLE)
            .where(this.condition())
            .limit(this.nbperpage)
            .offset(this.nbperpage * (this.page - 1))
            .fetch(
                rec -> new DbSubBundle(
                    this.src, rec.getId()
                )
            );
    }

    @Override
    public Long count() {
        return Long.valueOf(
            this.ctx
                .fetchCount(MaSubBundle.MA_SUB_BUNDLE, this.condition())
        );
    }

    private Condition condition() {
        Condition result = DSL.trueCondition();
        if (StringUtils.isNotBlank(this.filter)) {
            result = result.and(
                DSL.falseCondition()
                    .or(MaSubBundle.MA_SUB_BUNDLE.NO.like(this.filter + "%"))
            );
        }
        result = result.and(
            MaSubBundle.MA_SUB_BUNDLE.FISCAL_YEAR.eq(this.year)
        ).and(
            MaSubBundle.MA_SUB_BUNDLE.BUNDLE_SPLIT_WARRANT.eq(false)
        );
        if (this.bundle != Bundle.EMPTY) {
            result = result.and(
                MaSubBundle.MA_SUB_BUNDLE.BUNDLE.eq(this.bundle.code())
            );
        }
        if (this.section != Section.EMPTY) {
            result = result.and(
                MaSubBundle.MA_SUB_BUNDLE.SECTION.eq(this.section.code())
            );
        }
        if (this.title != Title.EMPTY) {
            result = result.and(
                MaSubBundle.MA_SUB_BUNDLE.TITLE.eq(this.title.code())
            );
        }
        return result;
    }
}
