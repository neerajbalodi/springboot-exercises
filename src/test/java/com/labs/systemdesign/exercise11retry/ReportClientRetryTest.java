package com.labs.systemdesign.exercise11retry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReportClientRetryTest {

    @Autowired
    ReportClient client;

    @MockBean
    RemoteReports remote;

    @Test
    void transientFailures_areRetried_thenSucceed() {
        when(remote.fetch("r1"))
                .thenThrow(new RemoteException("boom"))
                .thenThrow(new RemoteException("boom"))
                .thenReturn("REPORT_r1");   // 3rd attempt succeeds

        assertThat(client.fetchReport("r1"))
                .as("should keep retrying transient failures up to the attempt limit")
                .isEqualTo("REPORT_r1");
    }

    @Test
    void persistentFailure_fallsBackViaRecover() {
        when(remote.fetch("r2")).thenThrow(new RemoteException("still down"));

        assertThat(client.fetchReport("r2"))
                .as("after exhausting retries, @Recover should supply the fallback")
                .isEqualTo(ReportClient.FALLBACK);
    }
}
