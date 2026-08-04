package com.labs.systemdesign.exercise10nplus1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * EXERCISE 10 — Kill the N+1.
 *
 * The order-list screen shows each order with its buyer name and its line items.
 * findAllWithDetails() currently loads only the orders; touching buyer and items
 * then fires one extra query PER order (the classic N+1). With 4 orders that is
 * ~9 SQL statements.
 *
 * Fix the query:
 *   TODO: fetch buyer and items in one round trip using JOIN FETCH (add
 *         `join fetch o.buyer join fetch o.items`, and `distinct` to collapse the
 *         row multiplication from the items join).
 *
 * The test counts executed statements via Hibernate statistics and requires the
 * whole load to stay at a small, constant number regardless of order count.
 */
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    // TODO: add join fetch for buyer and items (and distinct).
    @Query("select o from PurchaseOrder o")
    List<PurchaseOrder> findAllWithDetails();
}
