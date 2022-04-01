package io.surati.gap.maccount.module.domain.secure;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.maccount.module.ManagementAccountAccess;
import io.surati.gap.maccount.module.domain.api.Title;
import io.surati.gap.maccount.module.domain.api.Titles;

public final class SecTitles implements Titles {

    private final Titles origin;

    private final User user;

    public SecTitles(final Titles origin, final User user) {
        this.origin = origin;
        this.user = user;
    }

    @Override
    public Title get(Long id) {
        return this.origin.get(id);
    }

    @Override
    public Iterable<Title> iterate() {
        return this.origin.iterate();
    }

    @Override
    public Title add(String code, String name, String notes) {
        if(!user.profile().accesses().has(ManagementAccountAccess.CONFIGURER_TITRES)) {
            throw new IllegalArgumentException("Vos droits d’accès sont insuffisants pour mener cette action.");
        }
        return this.origin.add(code, name, notes);
    }

    @Override
    public void remove(Long id) {
        if(!user.profile().accesses().has(ManagementAccountAccess.CONFIGURER_TITRES)) {
            throw new IllegalArgumentException("Vos droits d’accès sont insuffisants pour mener cette action.");
        }
        this.origin.remove(id);
    }

    @Override
    public boolean has(String code) {
        return this.origin.has(code);
    }
}
