package com.guardiants.platform.billing.infrastructure.events;

import com.guardiants.platform.billing.application.internal.outboundservices.events.SubscriptionEventPublisher;
import com.guardiants.platform.billing.domain.model.events.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringSubscriptionEventPublisher implements SubscriptionEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public SpringSubscriptionEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publishSubscriptionActivated(SubscriptionActivatedEvent event) {
        eventPublisher.publishEvent(event);
    }

    @Override
    public void publishSubscriptionSuspended(SubscriptionSuspendedEvent event) {
        eventPublisher.publishEvent(event);
    }

    @Override
    public void publishSubscriptionExpired(SubscriptionExpiredEvent event) {
        eventPublisher.publishEvent(event);
    }
}