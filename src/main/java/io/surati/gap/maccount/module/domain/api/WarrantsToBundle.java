package io.surati.gap.maccount.module.domain.api;

import io.surati.gap.admin.base.api.User;
import io.surati.gap.gtp.base.api.AnnualWarrants;

public interface WarrantsToBundle extends AnnualWarrants {

    /**
     * Bundle current selection.
     * @param author Author
     */
    void bundle(User author);

    /**
     * Bundle any way selection.
     * @param author Author
     */
    void bundleAnyway(User author);

}
