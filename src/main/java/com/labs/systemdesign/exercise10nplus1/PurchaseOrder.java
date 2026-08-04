package com.labs.systemdesign.exercise10nplus1;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Buyer buyer;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LineItem> items = new ArrayList<>();

    protected PurchaseOrder() {}
    public PurchaseOrder(Buyer buyer) { this.buyer = buyer; }

    public Long getId() { return id; }
    public Buyer getBuyer() { return buyer; }
    public List<LineItem> getItems() { return items; }

    public void addItem(LineItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
