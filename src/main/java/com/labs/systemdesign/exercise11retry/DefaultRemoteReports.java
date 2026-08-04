package com.labs.systemdesign.exercise11retry;

import org.springframework.stereotype.Component;

/** Real implementation so the app boots; the test replaces this with a mock. */
@Component
public class DefaultRemoteReports implements RemoteReports {
    @Override
    public String fetch(String id) {
        return "REPORT_" + id;
    }
}
