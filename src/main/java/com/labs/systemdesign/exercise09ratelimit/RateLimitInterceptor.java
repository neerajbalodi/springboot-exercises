package com.labs.systemdesign.exercise09ratelimit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Wires TokenBucket into the request path: one bucket per X-Client-Id.
 *
 * TODO (after TokenBucket works):
 *   - get-or-create this client's bucket (computeIfAbsent), e.g. 100 tokens,
 *     refill 100/60s.
 *   - if bucket.tryConsume() -> allow (return true).
 *   - else set status 429 (TOO_MANY_REQUESTS) and return false.
 *
 * Note for discussion: this in-memory map only limits per instance. Behind a load
 * balancer with N instances the real limit becomes N x intended -> use Redis.
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final Map<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String clientId = request.getHeader("X-Client-Id");
        // TODO: per-client bucket + 429 when exhausted.
        return true;
    }
}
