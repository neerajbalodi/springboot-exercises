package com.labs.systemdesign.exercise01idempotency;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EXERCISE 01 — Idempotent payments.
 *
 * The client retries on timeout, so pay() can be called twice with the SAME
 * idempotency key. It must charge the card at most once and return the same
 * result both times.
 *
 * Complete pay() so that:
 *   TODO 1: if the key already exists, return the stored result WITHOUT charging again.
 *   TODO 2: otherwise charge via gateway, then persist a PaymentRecord keyed by the
 *           idempotency key.
 *   TODO 3: handle the race where two calls with the same key arrive together — rely on
 *           the DB primary key (catch DataIntegrityViolationException) rather than an
 *           in-memory "if exists" check, which has a check-then-act gap.
 */
@Service
public class PaymentService {

    private final PaymentRecordRepository repo;
    private final PaymentGateway gateway;

    public PaymentService(PaymentRecordRepository repo, PaymentGateway gateway) {
        this.repo = repo;
        this.gateway = gateway;
    }

    @Transactional
    public PaymentResult pay(String idempotencyKey, PaymentRequest req) {
        // TODO: implement idempotent payment. Delete the line below.
        throw new UnsupportedOperationException("Complete PaymentService.pay(...)");
    }
}
