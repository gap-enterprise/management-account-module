package io.surati.gap.maccount.module.domain.db;

import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Treasury;
import io.surati.gap.maccount.module.domain.api.ManagementAccount;
import io.surati.gap.maccount.module.domain.api.ManagementAccountLine;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaManagementAccountView;
import io.surati.gap.maccount.module.domain.impl.CachedManagementAccountLine;
import io.surati.gap.maccount.module.domain.impl.CachedSection;
import javax.sql.DataSource;
import org.cactoos.Scalar;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public final class DbManagementAccount implements ManagementAccount {

    private final DSLContext ctx;

    private final Treasury treasury;

    private final short year;

    private final Scalar<Iterable<ManagementAccountLine>> sclines;

    private final Scalar<Double> scamdg;

    private final Scalar<Integer> scnbb;

    private final Scalar<Double> scamop;

    public DbManagementAccount(final DataSource src, final Treasury treasury, final short year) {
        this.ctx = new JooqContext(src);
        this.treasury = treasury;
        this.year = year;
        this.sclines = new Sticky<>(
            () -> this.ctx
                .selectFrom(MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW)
                .where(
                    MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.FISCAL_YEAR.eq(this.year)
                        .and(
                            MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.TREASURY_ID.eq(this.treasury.id())
                        )
                )
                .fetch(
                    rec -> new CachedManagementAccountLine(
                        new CachedSection(
                            rec.getSectionCode(),
                            rec.getSectionName()
                        ),
                        rec.getAmountInDigits(),
                        rec.getNumberOfBundles().intValue(),
                        rec.getAmountOnParts()
                    )
                )
        );
        this.scamdg = new Sticky<>(
            () -> this.ctx
                .select(DSL.sum(MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.AMOUNT_IN_DIGITS))
                .from(MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW)
                .where(
                    MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.FISCAL_YEAR.eq(this.year)
                        .and(
                            MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.TREASURY_ID.eq(this.treasury.id())
                        )
                )
                .fetchOne()
                .value1()
                .doubleValue()
        );
        this.scnbb = new Sticky<>(
            () -> this.ctx
                .select(DSL.sum(MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.NUMBER_OF_BUNDLES))
                .from(MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW)
                .where(
                    MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.FISCAL_YEAR.eq(this.year)
                        .and(
                            MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.TREASURY_ID.eq(this.treasury.id())
                        )
                )
                .fetchOne()
                .value1()
                .intValue()
        );
        this.scamop = new Sticky<>(
            () -> this.ctx
                .select(DSL.sum(MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.AMOUNT_ON_PARTS))
                .from(MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW)
                .where(
                    MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.FISCAL_YEAR.eq(this.year)
                        .and(
                            MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.TREASURY_ID.eq(this.treasury.id())
                        )
                )
                .fetchOne()
                .value1()
                .doubleValue()
        );
    }

    @Override
    public Treasury treasury() {
        return this.treasury;
    }

    @Override
    public int year() {
        return this.year;
    }

    @Override
    public Iterable<ManagementAccountLine> lines() {
        return new Unchecked<>(this.sclines).value();
    }

    @Override
    public Double totalAmountInDigits() {
        return new Unchecked<>(this.scamdg).value();
    }

    @Override
    public int totalNumberOfBundles() {
        return new Unchecked<>(this.scnbb).value();
    }

    @Override
    public Double totalAmountOnParts() {
        return new Unchecked<>(this.scamop).value();
    }
}
