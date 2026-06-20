package com.guardiants.platform.fleet.interfaces.rest.resources;

public record AlertRuleResource(Long id, Long fleetId, Long vehicleId, String type,
        GeofenceResource geofence, Double speedThresholdKmh,
        Integer prolongedStopThresholdMinutes, boolean enabled) {}
