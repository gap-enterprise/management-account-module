package io.surati.gap.maccount.module.domain.api;

public interface DocumentsToBundle {

    Iterable<DocumentToBundle> iterate();

    Long count();

    /**
     * Get a bundle.
     * @param id Identifier
     * @return Bundle
     */
    DocumentToBundle get(Long id);

    boolean has(final Long id);

    Double totalAmount();
}
