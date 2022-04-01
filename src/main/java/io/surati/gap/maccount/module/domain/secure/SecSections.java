package io.surati.gap.maccount.module.domain.secure;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.maccount.module.ManagementAccountAccess;
import io.surati.gap.maccount.module.domain.api.Section;
import io.surati.gap.maccount.module.domain.api.Sections;

public final class SecSections implements Sections {

    private final Sections origin;

    private final User user;

    public SecSections(final Sections origin, final User user) {
        this.origin = origin;
        this.user = user;
    }

    @Override
    public Section get(Long id) {
        return this.origin.get(id);
    }

    @Override
    public Iterable<Section> iterate() {
        return this.origin.iterate();
    }

    @Override
    public Section add(String code, String name, String notes) {
        if(!user.profile().accesses().has(ManagementAccountAccess.CONFIGURER_SECTIONS)) {
            throw new IllegalArgumentException("Vos droits d’accès sont insuffisants pour mener cette action.");
        }
        return this.origin.add(code, name, notes);
    }

    @Override
    public void remove(Long id) {
        if(!user.profile().accesses().has(ManagementAccountAccess.CONFIGURER_SECTIONS)) {
            throw new IllegalArgumentException("Vos droits d’accès sont insuffisants pour mener cette action.");
        }
        this.origin.remove(id);
    }

    @Override
    public boolean has(String code) {
        return this.origin.has(code);
    }
}
