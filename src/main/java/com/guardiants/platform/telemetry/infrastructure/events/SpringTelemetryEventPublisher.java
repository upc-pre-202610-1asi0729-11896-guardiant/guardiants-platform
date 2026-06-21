package com.guardiants.platform.telemetry.infrastructure.events;

import com.guardiants.platform.telemetry.application.internal.outboundservices.events.TelemetryEventPublisher;
import com.guardiants.platform.telemetry.domain.model.events.TelemetryPointRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringTelemetryEventPublisher implements TelemetryEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public SpringTelemetryEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publishPointRegistered(TelemetryPointRegisteredEvent event) {
        eventPublisher.publishEvent(event);
    }
}
