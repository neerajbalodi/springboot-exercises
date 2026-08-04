package com.labs.systemdesign.exercise11retry;

import org.springframework.stereotype.Service;

/**
 * EXERCISE 11 — Retry that doesn't make an outage worse.
 *
 * The downstream occasionally fails transiently. A bare retry loop (or retrying
 * everything, forever, with no delay) turns a blip into a retry storm and can
 * knock the downstream over. You want: a few attempts, exponential backoff with
 * jitter, retry ONLY transient failures, and a graceful fallback when it still
 * fails.
 *
 * Complete fetchReport():
 *   TODO 1: annotate it with @Retryable so it retries ONLY on RemoteException,
 *           max 3 attempts, with @Backoff(delay = 20, multiplier = 2, random = true).
 *   TODO 2: add a @Recover method (RemoteException e, String id) that returns the
 *           fallback FALLBACK so callers get a value instead of an exception.
 *
 * (Spring Retry is enabled via @EnableRetry on LabsApplication.)
 *
 * The starter has no retry and no recover, so a downstream that fails the first
 * couple of calls makes this throw, and the "eventually succeeds" test fails.
 */
@Service
public class ReportClient {

    public static final String FALLBACK = "REPORT_UNAVAILABLE";

    private final RemoteReports remote;

    public ReportClient(RemoteReports remote) {
        this.remote = remote;
    }

    // TODO 1: add @Retryable(retryFor = RemoteException.class, maxAttempts = 3,
    //                        backoff = @Backoff(delay = 20, multiplier = 2, random = true))
    public String fetchReport(String id) {
        return remote.fetch(id);
    }

    // TODO 2: add a @Recover method here that returns FALLBACK.
}
