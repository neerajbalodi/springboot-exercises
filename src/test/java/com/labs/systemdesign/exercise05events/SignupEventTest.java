package com.labs.systemdesign.exercise05events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SignupEventTest {

    @Autowired
    SignupService signupService;

    @Autowired
    EventSink sink;

    @BeforeEach
    void reset() {
        sink.emailsSent.set(0);
        sink.metricsRecorded.set(0);
    }

    @Test
    void signup_fansOutToBothConsumers() {
        signupService.signup("neeraj@example.com");

        assertThat(sink.emailsSent.get())
                .as("email listener should have reacted to the signup")
                .isEqualTo(1);
        assertThat(sink.metricsRecorded.get())
                .as("analytics listener should have reacted to the same event")
                .isEqualTo(1);
    }
}
