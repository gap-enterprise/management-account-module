package io.surati.gap.maccount.module.domain.api;

/**
 * All sections.
 * @since 3.0
 */
public interface Sections {

    /**
     * Get a section.
     * @param id Identifier
     * @return Title
     */
    Section get(Long id);

    /**
     * Iterate them all.
     * @return List of Sections
     */
    Iterable<Section> iterate();

    /**
     * Add a section.
     * @param code Code
     * @param name Name
     * @param notes Notes
     * @return Section added
     */
    Section add(String code, String name, String notes);

    /**
     * Remove a section.
     * @param id Identifier
     */
    void remove(Long id);

	boolean has(String code);

}
