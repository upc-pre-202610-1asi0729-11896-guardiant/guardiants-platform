package com.guardiants.platform.fleet.interfaces.rest.resources;

public record UpdateAlertRuleResource(Boolean enabled, Double speedThresholdKmh,
        Integer prolongedStopThresholdMinutes, GeofenceResource geofence) {}
