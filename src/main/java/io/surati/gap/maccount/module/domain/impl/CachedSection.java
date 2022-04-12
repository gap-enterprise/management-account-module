package io.surati.gap.maccount.module.domain.impl;

import io.surati.gap.gtp.base.api.Section;
import org.apache.commons.lang3.StringUtils;

public final class CachedSection implements Section {

    private final String code;

    private final String name;

    public CachedSection(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String notes() {
        return StringUtils.EMPTY;
    }

    @Override
    public String fullName() {
        return String.format("%s - %s", this.code, this.name);
    }

    @Override
    public void update(final String s, final String s1) {
        throw new UnsupportedOperationException("CachedSection#update");
    }
}
