package com.guardiants.platform.fleet.domain.model.commands;

import com.guardiants.platform.fleet.domain.model.valueobjects.AlertRuleType;
import com.guardiants.platform.fleet.domain.model.valueobjects.Geofence;

public record CreateAlertRuleCommand(Long fleetId, Long vehicleId,
        AlertRuleType type, Geofence geofence,
        Double speedThresholdKmh, Integer prolongedStopThresholdMinutes) {}
