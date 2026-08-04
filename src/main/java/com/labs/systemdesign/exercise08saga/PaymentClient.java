package com.labs.systemdesign.exercise08saga;

import java.math.BigDecimal;

public interface PaymentClient {
    String charge(String card, BigDecimal amount);  // returns a chargeId
    void refund(String chargeId);                    // compensating action
}
