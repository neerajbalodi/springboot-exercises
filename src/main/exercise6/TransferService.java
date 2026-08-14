package com.labs.systemdesign.exercise06transfer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * EXERCISE 06 — Move money without lost updates or overdrafts.
 *
 * Two concurrent transfers can each read the same balance, both decide there is
 * enough money, and both commit — leaving the account overdrawn (the "lost
 * update" problem). @Transactional alone does not prevent this at the default
 * isolation level.
 *
 * Complete transfer():
 *   TODO 1: reject the transfer with InsufficientFundsException if the source
 *           balance is smaller than the amount (do this BEFORE moving money).
 *   TODO 2: debit the source and credit the destination.
 *   TODO 3: make it safe under concurrency — add @Version to Account (optimistic)
 *           or use a PESSIMISTIC_WRITE finder for the source account. See README.
 *
 * The starter moves money with no balance check, so the overdraft test fails.
 */
@Service
public class TransferService {

    private final AccountRepository repo;

    public TransferService(AccountRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Account from = repo.findById(fromId).orElseThrow();
        Account to = repo.findById(toId).orElseThrow();

        // TODO 1: guard against insufficient funds here.

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
    }
}
