package com.guardiants.platform.alerting.domain.model.events;

import com.guardiants.platform.alerting.domain.model.valueobjects.AlertSeverity;

public record SecurityAlertGeneratedEvent(
        Long alertId, Long ownerId, Long vehicleId, AlertSeverity severity) {
}
