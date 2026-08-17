package com.labs.systemdesign.exercise07pagination;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Named OrderRow to avoid clashing with the SQL keyword ORDER when Hibernate
 * generates DDL/queries.
 */
@Entity
public class OrderRow {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long customerId;
    private String description;

    protected OrderRow() {}

    public OrderRow(Long customerId, String description) {
        this.customerId = customerId; this.description = description;
    }

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public String getDescription() { return description; }
}
