package com.guardiants.platform.alerting.domain.model.commands;

import com.guardiants.platform.alerting.domain.model.valueobjects.AlertRuleType;
import com.guardiants.platform.alerting.domain.model.valueobjects.Geofence;

public record CreateAlertRuleCommand(
        Long ownerId,
        Long vehicleId,
        AlertRuleType type,
        Geofence geofence,
        Double speedThresholdKmh,
        Integer prolongedStopThresholdMinutes) {
}
