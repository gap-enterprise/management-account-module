package io.surati.gap.maccount.module.domain.secure;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.maccount.module.ManagementAccountAccess;
import io.surati.gap.maccount.module.domain.api.Bundle;
import io.surati.gap.maccount.module.domain.api.Bundles;

public final class SecBundles implements Bundles {

    private final Bundles origin;

    private final User user;

    public SecBundles(final Bundles origin, final User user) {
        this.origin = origin;
        this.user = user;
    }

    @Override
    public Bundle get(Long id) {
        return this.origin.get(id);
    }

    @Override
    public Iterable<Bundle> iterate() {
        return this.origin.iterate();
    }

    @Override
    public Bundle add(String code, String notes) {
        if(!user.profile().accesses().has(ManagementAccountAccess.CONFIGURER_LIASSES)) {
            throw new IllegalArgumentException("Vos droits d’accès sont insuffisants pour mener cette action.");
        }
        return this.origin.add(code, notes);
    }

    @Override
    public void remove(Long id) {
        if(!user.profile().accesses().has(ManagementAccountAccess.CONFIGURER_LIASSES)) {
            throw new IllegalArgumentException("Vos droits d’accès sont insuffisants pour mener cette action.");
        }
        this.origin.remove(id);
    }

    @Override
    public boolean has(String code) {
        return this.origin.has(code);
    }
}
