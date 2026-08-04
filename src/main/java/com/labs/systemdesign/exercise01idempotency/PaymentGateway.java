package com.labs.systemdesign.exercise01idempotency;

import java.math.BigDecimal;

/**
 * Talks to the real card processor. Calling charge() twice = customer charged twice.
 */
public interface PaymentGateway {
    String charge(String cardToken, BigDecimal amount);
}
