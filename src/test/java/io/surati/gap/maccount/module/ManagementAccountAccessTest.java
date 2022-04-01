package io.surati.gap.maccount.module;

import io.surati.gap.admin.base.api.Access;
import io.surati.gap.payment.base.module.PaymentBaseAccess;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link ManagementAccountAccess}.
 *
 * @since 0.1
 */
final class ManagementAccountAccessTest {

    @Test
    void containsRight() {
        MatcherAssert.assertThat(
            Access.VALUES,
            Matchers.hasItem(ManagementAccountAccess.CONFIGURER_LIASSES)
        );
    }
}
