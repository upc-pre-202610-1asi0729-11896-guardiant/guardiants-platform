package com.guardiants.platform.alerting.interfaces.rest.resources;

public record AlertRuleResource(
        Long id,
        Long ownerId,
        Long vehicleId,
        String type,
        GeofenceResource geofence,
        Double speedThresholdKmh,
        Integer prolongedStopThresholdMinutes,
        boolean enabled) {
}
