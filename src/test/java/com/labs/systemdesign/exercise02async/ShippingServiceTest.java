package com.labs.systemdesign.exercise02async;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class ShippingServiceTest {

    /** A client that is slower than the service's timeout. */
    static ShippingClient slowClient(long sleepMs) {
        return zip -> {
            try { Thread.sleep(sleepMs); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            return "RATE_" + zip;
        };
    }

    @Test
    void slowDependency_fallsBackWithinTimeout() throws Exception {
        // Client takes 2s; service timeout is 300ms -> must fall back.
        ShippingService service = new ShippingService(slowClient(2000));

        long start = System.currentTimeMillis();
        String rate = service.getRateWithFallback("110001").get(1, TimeUnit.SECONDS);
        long elapsed = System.currentTimeMillis() - start;

        assertThat(rate)
                .as("slow dependency must yield the fallback rate")
                .isEqualTo(ShippingService.FALLBACK_RATE);
        assertThat(elapsed)
                .as("must not wait for the slow dependency to finish")
                .isLessThan(1500);
    }

    @Test
    void fastDependency_returnsRealRate() throws Exception {
        ShippingService service = new ShippingService(slowClient(10));

        String rate = service.getRateWithFallback("560001").get(1, TimeUnit.SECONDS);

        assertThat(rate).isEqualTo("RATE_560001");
    }
}
