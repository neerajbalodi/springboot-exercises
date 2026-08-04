package com.labs.systemdesign.exercise01idempotency;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * DataJpaTest gives a real (H2) repository. We wire the service by hand with a
 * fake gateway that counts how many times the card was actually charged.
 */
@DataJpaTest
class PaymentServiceTest {

    @Autowired
    PaymentRecordRepository repo;

    /** Fake processor: counts charges and hands back a unique id each time. */
    static class CountingGateway implements PaymentGateway {
        final AtomicInteger charges = new AtomicInteger();
        @Override
        public String charge(String cardToken, BigDecimal amount) {
            return "pay_" + charges.incrementAndGet();
        }
    }

    @Test
    void duplicateKey_chargesOnce_andReturnsSameResult() {
        CountingGateway gateway = new CountingGateway();
        PaymentService service = new PaymentService(repo, gateway);

        String key = "abc-123";
        PaymentRequest req = new PaymentRequest("tok_visa", new BigDecimal("100.00"));

        PaymentResult first = service.pay(key, req);
        PaymentResult second = service.pay(key, req);   // retry with same key

        assertThat(gateway.charges.get())
                .as("card must be charged exactly once for a repeated idempotency key")
                .isEqualTo(1);
        assertThat(second.paymentId())
                .as("second call must return the SAME payment, not a new one")
                .isEqualTo(first.paymentId());
        assertThat(repo.count()).isEqualTo(1);
    }

    @Test
    void differentKeys_chargeIndependently() {
        CountingGateway gateway = new CountingGateway();
        PaymentService service = new PaymentService(repo, gateway);

        service.pay("key-1", new PaymentRequest("tok", new BigDecimal("10.00")));
        service.pay("key-2", new PaymentRequest("tok", new BigDecimal("20.00")));

        assertThat(gateway.charges.get()).isEqualTo(2);
        assertThat(repo.count()).isEqualTo(2);
    }
}
