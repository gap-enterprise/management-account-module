package io.surati.gap.maccount.module.domain.api;

public interface WarrantsToBundle {

    /**
     * Get a bundle.
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

}
