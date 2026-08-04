package com.labs.systemdesign.exercise02async;

/**
 * Simulates a slow, flaky external shipping-rate provider.
 * In the real world this takes 2-4 seconds and occasionally hangs.
 */
public interface ShippingClient {
    String getRate(String zip);
}
