package io.surati.gap.maccount.module.domain.api;

/**
 * Section.
 * <p>Used for management account.</p>
 *
 * @since 3.0
 */
public interface Section {

    /**
     * Identifier.
     * @return Number
     */
    Long id();

    /**
     * Get code.
     * <p>Must be unique but can be modified</p>
     * @return Code
     */
    String code();

    /**
     * Get name.
     * @return Name
     */
    String name();

    /**
     * Get some notes or description.
     * @return Notes
     */
    String notes();
    
    /**
     * Get the code and the name of a section.
     * @return Code - Name
     */
    String fullName();
    
    /**
     * Update.
     * @param code Code
     * @param name Name
     * @param notes Notes
     */
    void update(String code, String name, String notes);

    Section EMPTY = new Section() {

        @Override
        public Long id() {
            return 0L;
        }

        @Override
        public String code() {
            return null;
        }

        @Override
        public String name() {
            return null;
        }

        @Override
        public String notes() {
            return null;
        }

        @Override
        public String fullName() {
            return null;
        }

        @Override
        public void update(String code, String name, String notes) {

        }
    };
}
