package com.labs.systemdesign.exercise05events;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test-visible stand-in for the side effects the listeners perform (sending an
 * email, recording a metric). Real listeners would call real collaborators; here
 * they just bump a counter so the test can prove they fired.
 */
@Component
public class EventSink {
    public final AtomicInteger emailsSent = new AtomicInteger();
    public final AtomicInteger metricsRecorded = new AtomicInteger();
}
