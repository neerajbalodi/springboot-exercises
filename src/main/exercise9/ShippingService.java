package com.labs.systemdesign.exercise02async;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * EXERCISE 02 — Don't let a slow dependency exhaust the request thread pool.
 *
 * The shipping call can take seconds. Blocking a Tomcat thread on it means that
 * under load every worker thread ends up parked in this call and the whole app
 * stops responding.
 *
 * Complete getRateWithFallback() so that:
 *   TODO 1: the call runs off the caller's thread (CompletableFuture / executor),
 *           so the web thread is not blocked waiting.
 *   TODO 2: it gives up after TIMEOUT_MS milliseconds.
 *   TODO 3: on timeout OR any failure it completes with FALLBACK_RATE instead of
 *           failing the whole request.
 *
 * The starter below blocks and has no timeout, so the test's slow client makes it
 * return the real (late) rate instead of the fallback -> test goes red.
 */
@Service
public class ShippingService {

    public static final long TIMEOUT_MS = 300;
    public static final String FALLBACK_RATE = "RATE_UNKNOWN";

    private final ShippingClient client;

    public ShippingService(ShippingClient client) {
        this.client = client;
    }

    public CompletableFuture<String> getRateWithFallback(String zip) {
        // TODO: run async, apply a TIMEOUT_MS timeout, fall back to FALLBACK_RATE.
        // Starter (wrong): blocks on the caller's thread, never times out.
        return CompletableFuture.completedFuture(client.getRate(zip));
    }
}
