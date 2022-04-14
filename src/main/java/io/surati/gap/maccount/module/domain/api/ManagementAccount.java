package io.surati.gap.maccount.module.domain.api;

import io.surati.gap.gtp.base.api.Treasury;

public interface ManagementAccount {

    Treasury treasury();

    int year();

    Iterable<ManagementAccountLine> lines();

    Double totalAmountInDigits();

    int totalNumberOfBundles();

    Double totalAmountOnParts();
}
