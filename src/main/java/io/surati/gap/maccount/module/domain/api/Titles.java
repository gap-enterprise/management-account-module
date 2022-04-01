package io.surati.gap.maccount.module.domain.api;

/**
 * All titles.
 * @since 3.0
 */
public interface Titles {

    /**
     * Get a title.
     * @param id Identifier
     * @return Title
     */
    Title get(Long id);

    /**
     * Iterate them all.
     * @return List of Titles
     */
    Iterable<Title> iterate();

    /**
     * Add a title.
     * @param code Code
     * @param name Name
     * @param notes Notes
     * @return Title added
     */
    Title add(String code, String name, String notes);

    /**
     * Remove an title.
     * @param id Identifier
     */
    void remove(Long id);

	boolean has(String code);

}
