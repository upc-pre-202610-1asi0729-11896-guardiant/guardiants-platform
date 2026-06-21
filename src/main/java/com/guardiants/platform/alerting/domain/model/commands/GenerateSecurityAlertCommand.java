package com.guardiants.platform.alerting.domain.model.commands;

import com.guardiants.platform.alerting.domain.model.valueobjects.*;

public record GenerateSecurityAlertCommand(
        Long ownerId,
        Long vehicleId,
        Long ruleId,
        AlertType type,
        AlertSeverity severity,
        GeoPoint location,
        String description) {
}
