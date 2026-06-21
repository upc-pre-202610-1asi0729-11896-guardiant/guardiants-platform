package com.guardiants.platform.telemetry.application.internal.eventhandlers;

import com.guardiants.platform.commands.domain.model.events.EngineBlockedEvent;
import com.guardiants.platform.telemetry.application.commandservices.TelemetryCommandService;
import com.guardiants.platform.telemetry.domain.model.commands.SetEngineLockStatusCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Reacts to the Commands BC {@link EngineBlockedEvent} by locking the engine on the
 * vehicle's general status. Built here (rather than in the Telemetry guide's feature 3)
 * because the event it listens to is only defined once the Commands BC exists.
 */
@Slf4j
@Component
public class EngineBlockedEventHandler {

    private final TelemetryCommandService telemetryCommandService;

    public EngineBlockedEventHandler(TelemetryCommandService telemetryCommandService) {
        this.telemetryCommandService = telemetryCommandService;
    }

    @EventListener
    public void on(EngineBlockedEvent event) {
        log.debug("EngineBlockedEvent received for vehicleId={}", event.vehicleId());
        telemetryCommandService.handle(
                new SetEngineLockStatusCommand(event.vehicleId(), true));
    }
}
