package io.surati.gap.maccount.module.domain.secure;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.maccount.module.ManagementAccountAccess;
import io.surati.gap.maccount.module.domain.api.Title;

public final class SecTitle implements Title {

    private final Title origin;

    private final User user;

    public SecTitle(final Title origin , final User user) {
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
    public String name() {
        return this.origin.name();
    }

    @Override
    public String notes() {
        return this.origin.notes();
    }

    @Override
    public String fullName() {
        return this.origin.fullName();
    }

    @Override
    public void update(String code, String name, String notes) {
        if(!user.profile().accesses().has(ManagementAccountAccess.CONFIGURER_TITRES)) {
            throw new IllegalArgumentException("Vos droits d’accès sont insuffisants pour mener cette action.");
        }
        this.origin.update(code, name, notes);
    }
}
