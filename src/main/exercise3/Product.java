package com.labs.systemdesign.exercise03caching;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    private Long id;
    private String name;
    private BigDecimal price;

    protected Product() {}

    public Product(Long id, String name, BigDecimal price) {
        this.id = id; this.name = name; this.price = price;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public void setName(String name) { this.name = name; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
