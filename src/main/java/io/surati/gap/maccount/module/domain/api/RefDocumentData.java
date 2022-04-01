package io.surati.gap.maccount.module.domain.api;

import io.surati.gap.payment.base.api.ReferenceDocument;

/**
 * Management account data of a reference document.
 *
 * @since 3.0
 */
public interface RefDocumentData {

    /**
     * Get reference document.
     * @return Document
     */
    ReferenceDocument document();

    /**
     * Get bundle.
     * @return Bundle
     */
    Bundle bundle();

    /**
     * Get title.
     * @return Title
     */
    Title title();

    /**
     * Get section.
     * @return
     */
    Section section();

    /**
     * Get imputation.
     * @return Reference
     */
    String imputation();

    /**
     * Update.
     * @param bundle Bundle
     * @param title Title
     * @param section Section
     * @param imputation Imputation
     */
    void update(Bundle bundle, Title title, Section section, String imputation);

    /**
     * Check if data are defined.
     * @return Yes or not
     */
    boolean isDefined();
}
