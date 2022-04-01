package io.surati.gap.maccount.module.domain.secure;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.maccount.module.ManagementAccountAccess;
import io.surati.gap.maccount.module.domain.api.Bundle;

public final class SecBundle implements Bundle {

    private final Bundle origin;

    private final User user;

    public SecBundle(final Bundle origin, final User user) {
        this.origin = origin;
        this.user = user;
    }

    @Override
    public Long id() {
        return this.origin.id();
    }

    @Override
    public String code() {
        return this.origin.code();
    }

    @Override
    public String notes() {
        return this.origin.notes();
    }

    @Override
    public void update(String code, String notes) {
        if(!user.profile().accesses().has(ManagementAccountAccess.CONFIGURER_LIASSES)) {
            throw new IllegalArgumentException("Vos droits d’accès sont insuffisants pour mener cette action.");
        }
        this.origin.update(code, notes);
    }
}
