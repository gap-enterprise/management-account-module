package io.surati.gap.maccount.module.domain.api;

import io.surati.gap.gtp.base.api.Warrant;

public interface AnnualWarrant extends Warrant {

    int order();

    int year();

    Double annualAmountToPay();

    Double annualAmountPaid();

    Double annualAmountLeft();
}