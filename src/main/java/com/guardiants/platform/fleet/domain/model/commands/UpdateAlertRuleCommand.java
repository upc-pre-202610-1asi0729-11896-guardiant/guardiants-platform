package com.guardiants.platform.fleet.domain.model.commands;

import com.guardiants.platform.fleet.domain.model.valueobjects.Geofence;

public record UpdateAlertRuleCommand(Long ruleId, Boolean enabled,
        Double speedThresholdKmh, Integer prolongedStopThresholdMinutes,
        Geofence geofence) {}
