package com.labs.systemdesign.exercise10nplus1;

import jakarta.persistence.*;

@Entity
public class LineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String sku;

    @ManyToOne(fetch = FetchType.LAZY)
    private PurchaseOrder order;

    protected LineItem() {}
    public LineItem(String sku) { this.sku = sku; }

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public void setOrder(PurchaseOrder order) { this.order = order; }
}
