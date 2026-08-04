package com.labs.systemdesign.exercise09ratelimit;

import java.util.function.LongSupplier;

/**
 * EXERCISE 09 — A token-bucket rate limiter.
 *
 * The bucket starts full (capacity tokens). Each allowed request consumes one
 * token. Tokens refill over time at refillPerSecond, never exceeding capacity.
 * When empty, requests are rejected (caller returns HTTP 429).
 *
 * The clock is injected so refill can be tested without real sleeping.
 *
 * Complete tryConsume():
 *   TODO 1: refill tokens based on how much time passed since the last refill
 *           (elapsed * refillPerSecond), capped at capacity.
 *   TODO 2: if at least one token is available, consume one and return true.
 *   TODO 3: otherwise return false.
 *
 * The starter always returns true (no limiting), so the exhaustion test fails.
 */
public class TokenBucket {

    private final double capacity;
    private final double refillPerMilli;
    private final LongSupplier clockMillis;

    private double tokens;
    private long lastRefillMillis;

    public TokenBucket(double capacity, double refillPerSecond, LongSupplier clockMillis) {
        this.capacity = capacity;
        this.refillPerMilli = refillPerSecond / 1000.0;
        this.clockMillis = clockMillis;
        this.tokens = capacity;
        this.lastRefillMillis = clockMillis.getAsLong();
    }

    public synchronized boolean tryConsume() {
        // TODO: refill based on elapsed time, then consume one token if available.
        return true; // starter: no limiting
    }
}
