package com.labs.systemdesign.exercise01idempotency;

import java.math.BigDecimal;

public record PaymentRequest(String cardToken, BigDecimal amount) {}
