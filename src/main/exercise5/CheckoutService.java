package com.labs.systemdesign.exercise08saga;

import org.springframework.stereotype.Service;

/**
 * EXERCISE 08 — Saga with compensating actions.
 *
 * Checkout spans three systems: reserve inventory -> charge card -> create order.
 * These are separate services, so there is no single database transaction to roll
 * back. If order creation fails AFTER the card was charged and stock reserved, you
 * must actively undo the earlier steps.
 *
 * Complete checkout():
 *   TODO 1: if orderStore.save(...) throws, release the inventory reservation and
 *           refund the charge (compensate), then rethrow.
 *   TODO 2: compensations should be safe to run even if one of them also fails
 *           (don't let a failing refund skip the release, or vice versa).
 *
 * The starter just rethrows without compensating, so the test that checks stock
 * was released and the card refunded fails.
 */
@Service
public class CheckoutService {

    private final InventoryClient inventory;
    private final PaymentClient payment;
    private final OrderStore orders;

    public CheckoutService(InventoryClient inventory, PaymentClient payment, OrderStore orders) {
        this.inventory = inventory;
        this.payment = payment;
        this.orders = orders;
    }

    public void checkout(CheckoutRequest req) {
        String reservationId = inventory.reserve(req.sku(), req.qty());
        String chargeId = payment.charge(req.card(), req.total());

        try {
            orders.save(req, reservationId, chargeId);
        } catch (RuntimeException e) {
            // TODO: compensate here (release reservation + refund charge), then rethrow.
            throw e;
        }
    }
}
