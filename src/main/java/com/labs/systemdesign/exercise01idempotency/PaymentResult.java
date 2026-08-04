package com.labs.systemdesign.exercise01idempotency;

import java.math.BigDecimal;

public record PaymentResult(String paymentId, BigDecimal amount, String status) {}
