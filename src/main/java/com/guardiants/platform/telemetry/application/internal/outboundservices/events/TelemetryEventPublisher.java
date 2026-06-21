package com.guardiants.platform.telemetry.application.internal.outboundservices.events;

import com.guardiants.platform.telemetry.domain.model.events.TelemetryPointRegisteredEvent;

public interface TelemetryEventPublisher {
    void publishPointRegistered(TelemetryPointRegisteredEvent event);
}
