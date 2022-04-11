package io.surati.gap.maccount.module.domain.api;

import io.surati.gap.admin.base.api.User;

public interface WarrantsToBundle {

    /**
     * Get an annual warrant.
     * @param id Identifier
     * @return Warrant
     */
    AnnualWarrant get(Long id);

    /**
     * Has warranty of identifier.
     * @param id Identifier
     * @return Has or not
     */
    boolean has(Long id);
    /**
     * Iterate them all.
     * @return
     */
    Iterable<AnnualWarrant> iterate();

    /**
     * Total.
     * @return Number of warrants
     */
    Long count();

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
