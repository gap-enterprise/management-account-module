package io.surati.gap.maccount.module.domain.impl;

import io.surati.gap.gtp.base.api.Section;
import io.surati.gap.maccount.module.domain.api.ManagementAccountLine;

public final class CachedManagementAccountLine implements ManagementAccountLine {

    private final Section section;

    private final Double amountdigits;

    private final int nbbundles;

    private final Double amountparts;

    public CachedManagementAccountLine(
        final Section section, final Double amountdigits,
        final int nbbundles, final Double amountparts
    ) {
        this.section = section;
        this.amountdigits = amountdigits;
        this.nbbundles = nbbundles;
        this.amountparts = amountparts;
    }
    @Override
    public Section section() {
        return this.section;
    }

    @Override
    public Double amountInDigits() {
        return this.amountdigits;
    }

    @Override
    public int numberOfBundles() {
        return this.nbbundles;
    }

    @Override
    public Double amountOnParts() {
        return this.amountparts;
    }
}
