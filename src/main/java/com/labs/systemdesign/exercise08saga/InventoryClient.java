package com.labs.systemdesign.exercise08saga;

public interface InventoryClient {
    String reserve(String sku, int qty);   // returns a reservationId
    void release(String reservationId);     // compensating action
}
