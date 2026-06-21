package com.guardiants.platform.alerting.domain.model.commands;

import com.guardiants.platform.alerting.domain.model.valueobjects.Geofence;

public record UpdateAlertRuleCommand(
        Long ruleId,
        Boolean enabled,
        Geofence geofence,
        Double speedThresholdKmh,
        Integer prolongedStopThresholdMinutes) {
}
