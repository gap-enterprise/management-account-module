package io.surati.gap.maccount.module.domain.db;

import io.surati.gap.admin.base.api.ReferenceDocumentType;
import io.surati.gap.admin.base.api.User;
import io.surati.gap.admin.base.db.DbUser;
import io.surati.gap.database.utils.jooq.JooqContext;
import io.surati.gap.gtp.base.api.Bundle;
import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.gtp.base.api.Title;
import io.surati.gap.gtp.base.db.DbBundle;
import io.surati.gap.gtp.base.db.DbSection;
import io.surati.gap.gtp.base.db.DbTitle;
import io.surati.gap.maccount.module.domain.api.AnnualWarrant;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaAnnualWarrant;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaAnnualWarrantView;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.records.MaAnnualWarrantRecord;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.records.MaAnnualWarrantViewRecord;
import io.surati.gap.payment.base.api.Payment;
import io.surati.gap.payment.base.api.PaymentOrder;
import io.surati.gap.payment.base.api.ReferenceDocumentStatus;
import io.surati.gap.payment.base.api.ReferenceDocumentStep;
import io.surati.gap.payment.base.api.ThirdParty;
import io.surati.gap.payment.base.db.DbThirdParty;
import java.time.LocalDate;
import javax.sql.DataSource;
import org.jooq.DSLContext;

public final class DbAnnualWarrant implements AnnualWarrant {

    private final DSLContext ctx;

    private final DataSource src;

    private final MaAnnualWarrantRecord record;

    private final MaAnnualWarrantViewRecord view;

    public DbAnnualWarrant(final DataSource src, final Long id, final short year) {
        this.src = src;
        this.ctx = new JooqContext(this.src);
        this.record = this.ctx.fetchOne(
            MaAnnualWarrant.MA_ANNUAL_WARRANT,
            MaAnnualWarrant.MA_ANNUAL_WARRANT.WARRANT_ID.eq(id).and(
                MaAnnualWarrant.MA_ANNUAL_WARRANT.FISCAL_YEAR.eq(year)
            )
        );
        this.view = this.ctx.fetchOne(
            MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW,
            MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.ID.eq(this.record.getWarrantId()).and(
                MaAnnualWarrantView.MA_ANNUAL_WARRANT_VIEW.FISCAL_YEAR.eq(this.record.getFiscalYear())
            )
        );
    }

    @Override
    public int order() {
        return this.view.getNo().intValue();
    }

    @Override
    public int year() {
        return this.record.getFiscalYear();
    }

    @Override
    public Double annualAmountToPay() {
        return this.record.getAnnualAmountToPay();
    }

    @Override
    public Double annualAmountPaid() {
        return this.record.getAnnualAmountPaid();
    }

    @Override
    public Double annualAmountLeft() {
        return this.record.getAnnualAmountLeft();
    }

    @Override
    public Title title() {
        return new DbTitle(this.src, this.view.getTitle());
    }

    @Override
    public Section section() {
        return new DbSection(this.src, this.view.getSection());
    }

    @Override
    public String imputation() {
        return this.view.getImputation();
    }

    @Override
    public Bundle bundle() {
        return new DbBundle(this.src, this.view.getBundle());
    }

    @Override
    public Long id() {
        return this.record.getWarrantId();
    }

    @Override
    public ThirdParty issuer() {
        return new DbThirdParty(this.src, this.view.getBeneficiaryId());
    }

    @Override
    public ReferenceDocumentType type() {
        return ReferenceDocumentType.valueOf(this.view.getTypeId());
    }

    @Override
    public LocalDate date() {
        return this.view.getDate();
    }

    @Override
    public String reference() {
        return this.view.getReference();
    }

    @Override
    public String otherReference() {
        return this.view.getInternalReference();
    }

    @Override
    public String object() {
        return this.view.getObject();
    }

    @Override
    public String place() {
        return this.view.getPlace();
    }

    @Override
    public LocalDate depositDate() {
        return this.view.getDepositDate();
    }

    @Override
    public LocalDate entryDate() {
        return this.view.getEntryDate();
    }

    @Override
    public Double amount() {
        return this.view.getAmount();
    }

    @Override
    public Double amountPaid() {
        return this.view.getAmountPaid();
    }

    @Override
    public Double amountLeft() {
        return this.view.getAmountLeft();
    }

    @Override
    public ReferenceDocumentStatus status() {
        return ReferenceDocumentStatus.valueOf(this.view.getStatusId());
    }

    @Override
    public void type(ReferenceDocumentType referenceDocumentType) {

    }

    @Override
    public void amount(Double aDouble, Double aDouble1) {

    }

    @Override
    public void update(LocalDate localDate, String s, String s1, String s2) {

    }

    @Override
    public void update(String s) {

    }

    @Override
    public void update(LocalDate localDate, LocalDate localDate1) {

    }

    @Override
    public Iterable<Payment> payments() {
        return null;
    }

    @Override
    public ReferenceDocumentStep step() {
        return ReferenceDocumentStep.valueOf(this.view.getStepId());
    }

    @Override
    public void sendToTreatment() {

    }

    @Override
    public void sendBackInPreparation() {

    }

    @Override
    public void sendInPayment() {

    }

    @Override
    public void archive() {

    }

    @Override
    public void updateState() {

    }

    @Override
    public User author() {
        return new DbUser(this.src, this.view.getAuthorId());
    }

    @Override
    public PaymentOrder preparePayment(User user) {
        return null;
    }

    @Override
    public void beneficiary(ThirdParty thirdParty) {

    }
}
