package com.labs.systemdesign.exercise05events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EXERCISE 05 — Fan out "user created" without coupling signup to consumers.
 *
 * Today email + analytics must react to a signup. Tomorrow it's a third thing.
 * signup() must NOT call those services directly — adding a consumer should not
 * mean editing this method.
 *
 * Complete signup():
 *   TODO: after saving the user, publish a UserCreatedEvent via the injected
 *         ApplicationEventPublisher. The listeners (EmailListener, AnalyticsListener)
 *         subscribe on their own.
 *
 * In production a @TransactionalEventListener(phase = AFTER_COMMIT) is the correct
 * choice so you never email a user whose signup rolled back — see README.
 */
@Service
public class SignupService {

    private final UserRepository repo;
    private final ApplicationEventPublisher publisher;

    public SignupService(UserRepository repo, ApplicationEventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    @Transactional
    public User signup(String email) {
        User user = repo.save(new User(email));
        // TODO: publish a UserCreatedEvent(user.getId(), user.getEmail()) here.
        return user;
    }
}
