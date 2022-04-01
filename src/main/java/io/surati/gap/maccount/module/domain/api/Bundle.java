package io.surati.gap.maccount.module.domain.api;

/**
 * Bundle.
 * @since 3.0
 */
public interface Bundle {

    /**
     * Get identifier.
     * @return Number
     */
    Long id();

    /**
     * Get code.
     * <p>It's unique but it can be modified.</p>
     * @return Code
     */
    String code();

    /**
     * Get some notes or description.
     * @return Notes
     */
    String notes();

    /**
     * Update.
     * @param code Code
     * @param notes Notes
     */
    void update(String code, String notes);

    Bundle EMPTY = new Bundle() {

        @Override
        public Long id() {
            return 0L;
        }

        @Override
        public String code() {
            return null;
        }

        @Override
        public String notes() {
            return null;
        }

        @Override
        public void update(String code, String notes) {

        }
    };
}
