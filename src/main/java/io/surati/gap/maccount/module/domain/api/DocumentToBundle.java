package io.surati.gap.maccount.module.domain.api;

import io.surati.gap.payment.base.api.ThirdParty;

public interface DocumentToBundle {

    Long id();

    String reference();

    String fullName();

    int yearOfPayment();

    Double totalAmount();

    Double annualAmountPaid();

    Double leftAmount();

    ThirdParty beneficiary();

    RefDocumentData maData();
}
