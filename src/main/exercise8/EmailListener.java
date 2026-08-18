package com.labs.systemdesign.exercise05events;

import org.springframework.stereotype.Component;

/**
 * TODO: react to UserCreatedEvent and "send" a welcome email by calling
 *       sink.emailsSent.incrementAndGet(). Add a handler method annotated with
 *       @EventListener (or @TransactionalEventListener) taking UserCreatedEvent.
 *
 * Right now nothing here listens, so no email is sent.
 */
@Component
public class EmailListener {

    private final EventSink sink;

    public EmailListener(EventSink sink) { this.sink = sink; }

    // TODO: add the @EventListener handler method.
}
