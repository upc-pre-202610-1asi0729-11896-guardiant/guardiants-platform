package com.guardiants.platform.fleet.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAlertRuleResource(@NotNull Long fleetId, Long vehicleId,
        @NotBlank String type, GeofenceResource geofence,
        Double speedThresholdKmh, Integer prolongedStopThresholdMinutes) {}
