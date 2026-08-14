package com.labs.systemdesign.exercise06transfer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
// TODO: you will likely need jakarta.persistence.Version
import java.math.BigDecimal;

@Entity
public class Account {

    @Id
    private Long id;
    private BigDecimal balance;

    // TODO: turn this into an optimistic-lock version column with @Version so two
    //       concurrent transfers on the same account can't both commit on stale data.
    private Long version;

    protected Account() {}

    public Account(Long id, BigDecimal balance) {
        this.id = id; this.balance = balance;
    }

    public Long getId() { return id; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
