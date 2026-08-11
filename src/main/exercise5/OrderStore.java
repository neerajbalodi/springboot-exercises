package com.labs.systemdesign.exercise08saga;

public interface OrderStore {
    void save(CheckoutRequest req, String reservationId, String chargeId);
}
