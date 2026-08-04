package com.labs.systemdesign.exercise09ratelimit;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

class TokenBucketTest {

    @Test
    void allowsUpToCapacity_thenRejects() {
        AtomicLong clock = new AtomicLong(0);
        TokenBucket bucket = new TokenBucket(3, 1, clock::get); // 3 tokens, refill 1/sec

        assertThat(bucket.tryConsume()).isTrue();
        assertThat(bucket.tryConsume()).isTrue();
        assertThat(bucket.tryConsume()).isTrue();
        assertThat(bucket.tryConsume())
                .as("4th request with no refill must be rejected")
                .isFalse();
    }

    @Test
    void refillsOverTime() {
        AtomicLong clock = new AtomicLong(0);
        TokenBucket bucket = new TokenBucket(3, 1, clock::get); // refill 1 token/sec

        bucket.tryConsume();
        bucket.tryConsume();
        bucket.tryConsume();                 // now empty
        assertThat(bucket.tryConsume()).isFalse();

        clock.addAndGet(1000);               // one second passes -> +1 token
        assertThat(bucket.tryConsume())
                .as("after 1s a single refilled token should be available")
                .isTrue();
        assertThat(bucket.tryConsume())
                .as("only one token refilled")
                .isFalse();
    }
}
