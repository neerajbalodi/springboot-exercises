package com.labs.systemdesign.exercise10nplus1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    protected Buyer() {}
    public Buyer(String name) { this.name = name; }

    public Long getId() { return id; }
    public String getName() { return name; }
}
