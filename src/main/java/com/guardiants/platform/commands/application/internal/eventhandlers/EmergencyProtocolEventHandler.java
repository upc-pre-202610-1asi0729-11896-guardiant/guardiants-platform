package com.guardiants.platform.commands.application.internal.eventhandlers;

import com.guardiants.platform.commands.domain.model.events.TheftReportRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Activates the emergency protocol when a theft report is registered. Logs the activation;
 * the Alerting BC may subscribe to {@link TheftReportRegisteredEvent} to generate a CRITICAL
 * THEFT_REPORTED security alert and trigger auto-defense.
 */
@Slf4j
@Component
public class EmergencyProtocolEventHandler {

    @EventListener
    public void on(TheftReportRegisteredEvent event) {
        log.info("EmergencyProtocolEventHandler: activating emergency protocol " +
                 "for vehicleId={} reportId={}", event.vehicleId(), event.reportId());
    }
}
