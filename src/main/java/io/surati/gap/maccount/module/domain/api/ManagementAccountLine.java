package io.surati.gap.maccount.module.domain.api;

import io.surati.gap.gtp.base.api.Section;

public interface ManagementAccountLine {

    Section section();

    Double amountInDigits();

    int numberOfBundles();

    Double amountOnParts();

}
