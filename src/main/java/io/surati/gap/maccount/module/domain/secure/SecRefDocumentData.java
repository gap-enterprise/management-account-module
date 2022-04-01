package io.surati.gap.maccount.module.domain.secure;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.maccount.module.ManagementAccountAccess;
import io.surati.gap.maccount.module.domain.api.Bundle;
import io.surati.gap.maccount.module.domain.api.RefDocumentData;
import io.surati.gap.maccount.module.domain.api.Section;
import io.surati.gap.maccount.module.domain.api.Title;
import io.surati.gap.payment.base.api.ReferenceDocument;

public final class SecRefDocumentData implements RefDocumentData {

    private final RefDocumentData origin;

    private final User user;

    public SecRefDocumentData(final RefDocumentData origin, final User user) {
        this.origin = origin;
        this.user = user;
    }

    @Override
    public ReferenceDocument document() {
        return this.origin.document();
    }

    @Override
    public Bundle bundle() {
        return this.origin.bundle();
    }

    @Override
    public Title title() {
        return this.origin.title();
    }

    @Override
    public Section section() {
        return this.origin.section();
    }

    @Override
    public String imputation() {
        return this.origin.imputation();
    }

    @Override
    public void update(Bundle bundle, Title title, Section section, String imputation) {
        if(!user.profile().accesses().has(ManagementAccountAccess.CONFIGURER_SEUILS_ENLIASSEMENT)) {
            throw new IllegalArgumentException("Vos droits d’accès sont insuffisants pour mener cette action.");
        }
        this.origin.update(bundle, title, section, imputation);
    }

    @Override
    public boolean isDefined() {
        return this.origin.isDefined();
    }
}
