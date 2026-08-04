package com.labs.systemdesign.exercise11retry;

/** The flaky downstream service. */
public interface RemoteReports {
    String fetch(String id);
}
