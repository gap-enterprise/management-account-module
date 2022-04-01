package io.surati.gap.maccount.module.domain.api;

public interface BundleThreshold {

    Integer partialPayment();

    Integer totalPayment();

    void update(Integer partialpay, Integer totalpay);
}
