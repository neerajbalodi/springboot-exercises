package com.labs.systemdesign.exercise01idempotency;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

/**
 * The idempotency key is the PRIMARY KEY. That single fact is what makes
 * a concurrent duplicate insert fail at the database instead of double-charging.
 */
@Entity
public class PaymentRecord {

    @Id
    private String idempotencyKey;
    private String paymentId;
    private BigDecimal amount;
    private String status;

    protected PaymentRecord() {}

    public PaymentRecord(String idempotencyKey, String paymentId, BigDecimal amount, String status) {
        this.idempotencyKey = idempotencyKey;
        this.paymentId = paymentId;
        this.amount = amount;
        this.status = status;
    }

    public String getIdempotencyKey() { return idempotencyKey; }
    public String getPaymentId() { return paymentId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
}
