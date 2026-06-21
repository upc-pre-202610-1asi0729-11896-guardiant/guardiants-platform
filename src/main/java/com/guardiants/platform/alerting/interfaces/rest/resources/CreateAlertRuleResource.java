package com.guardiants.platform.alerting.interfaces.rest.resources;

public record CreateAlertRuleResource(
        Long ownerId,
        Long vehicleId,
        String type,
        GeofenceResource geofence,
        Double speedThresholdKmh,
        Integer prolongedStopThresholdMinutes) {
}
