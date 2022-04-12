package io.surati.gap.maccount.module.domain.api;

public interface ManagementAccount {

    int year();

    Iterable<ManagementAccountLine> lines();

    Double totalAmountInDigits();

    int totalNumberOfBundles();

    Double totalAmountOnParts();
}
