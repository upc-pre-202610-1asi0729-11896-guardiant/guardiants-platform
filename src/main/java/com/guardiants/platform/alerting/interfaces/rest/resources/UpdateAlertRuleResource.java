package com.guardiants.platform.alerting.interfaces.rest.resources;

public record UpdateAlertRuleResource(
        Boolean enabled,
        GeofenceResource geofence,
        Double speedThresholdKmh,
        Integer prolongedStopThresholdMinutes) {
}
