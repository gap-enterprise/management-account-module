package io.surati.gap.maccount.module.domain.api;

public interface SubBundles {

    /**
     * Get a bundle.
     * @param id Identifier
     * @return Warrant
     */
    SubBundle get(Long id);

    /**
     * Has bundle of identifier.
     * @param id Identifier
     * @return Has or not
     */
    boolean has(Long id);
    /**
     * Iterate them all.
     * @return
     */
    Iterable<SubBundle> iterate();

    /**
     * Total.
     * @return Number of warrants
     */
    Long count();
}
