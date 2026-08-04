package com.labs.systemdesign.exercise08saga;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CheckoutSagaTest {

    static class FakeInventory implements InventoryClient {
        String released;
        public String reserve(String sku, int qty) { return "res-1"; }
        public void release(String reservationId) { this.released = reservationId; }
    }

    static class FakePayment implements PaymentClient {
        String refunded;
        public String charge(String card, BigDecimal amount) { return "chg-1"; }
        public void refund(String chargeId) { this.refunded = chargeId; }
    }

    static class FailingOrderStore implements OrderStore {
        public void save(CheckoutRequest req, String reservationId, String chargeId) {
            throw new RuntimeException("DB down");
        }
    }

    @Test
    void whenOrderCreationFails_reservationReleasedAndChargeRefunded() {
        FakeInventory inventory = new FakeInventory();
        FakePayment payment = new FakePayment();
        CheckoutService service = new CheckoutService(inventory, payment, new FailingOrderStore());

        CheckoutRequest req = new CheckoutRequest("SKU-1", 2, "tok", new BigDecimal("50.00"));

        assertThatThrownBy(() -> service.checkout(req))
                .isInstanceOf(RuntimeException.class);

        assertThat(inventory.released)
                .as("inventory reservation must be released on failure")
                .isEqualTo("res-1");
        assertThat(payment.refunded)
                .as("card charge must be refunded on failure")
                .isEqualTo("chg-1");
    }
}
