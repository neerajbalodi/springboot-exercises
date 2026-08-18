package com.labs.systemdesign.exercise05events;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String email;

    protected User() {}

    public User(String email) { this.email = email; }

    public Long getId() { return id; }
    public String getEmail() { return email; }
}
