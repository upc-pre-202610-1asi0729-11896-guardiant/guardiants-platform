package com.guardiants.platform.billing.infrastructure.events;

import com.guardiants.platform.billing.application.internal.outboundservices.events.PaymentEventPublisher;
import com.guardiants.platform.billing.domain.model.events.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringPaymentEventPublisher implements PaymentEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public SpringPaymentEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publishPaymentSucceeded(PaymentSucceededEvent event) {
        eventPublisher.publishEvent(event);
    }

    @Override
    public void publishPaymentFailed(PaymentFailedEvent event) {
        eventPublisher.publishEvent(event);
    }
}