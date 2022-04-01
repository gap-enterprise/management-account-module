package io.surati.gap.maccount.module.domain.api;

/**
 * All bundles.
 * @since 3.0
 */
public interface Bundles {

    /**
     * Get a bundle.
     * @param id Identifier
     * @return Bundle
     */
    Bundle get(Long id);

    /**
     * Iterate them all.
     * @return List of Bundles
     */
    Iterable<Bundle> iterate();

    /**
     * Add a bundle.
     * @param code Code
     * @param notes Notes
     * @return Bundle added
     */
    Bundle add(String code, String notes);

    /**
     * Remove a bundle.
     * @param id Identifier
     */
    void remove(Long id);

    /**
     * Has a bundle.
     * @param code Code
     * @return True or false
     */
	boolean has(String code);

}
