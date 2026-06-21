package com.guardiants.platform.telemetry.interfaces.rest.resources;

import java.time.Instant;

public record TelemetryPointResource(
        Long id,
        Long vehicleId,
        String deviceSerial,
        Instant timestamp,
        double lat,
        double lng,
        double speedKmh,
        Double fuelLevelPercent,
        Double engineTemperatureC,
        double batteryLevelPercent,
        String connectivityStatus) {
}
