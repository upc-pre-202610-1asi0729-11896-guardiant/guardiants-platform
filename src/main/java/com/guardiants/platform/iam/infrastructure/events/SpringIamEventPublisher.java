package com.guardiants.platform.iam.infrastructure.events;

import com.guardiants.platform.iam.application.internal.outboundservices.events.IamEventPublisher;
import com.guardiants.platform.iam.domain.model.events.AccountRegisteredEvent;
import com.guardiants.platform.iam.domain.model.events.EmailVerifiedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringIamEventPublisher implements IamEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public SpringIamEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publishAccountRegistered(AccountRegisteredEvent event) {
        eventPublisher.publishEvent(event);
    }

    @Override
    public void publishEmailVerified(EmailVerifiedEvent event) {
        eventPublisher.publishEvent(event);
    }
}