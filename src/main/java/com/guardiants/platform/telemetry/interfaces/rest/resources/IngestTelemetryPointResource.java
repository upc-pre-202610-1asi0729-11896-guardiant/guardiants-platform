package com.guardiants.platform.telemetry.interfaces.rest.resources;

import java.time.Instant;

public record IngestTelemetryPointResource(
        String deviceSerial,
        Long vehicleId,
        Instant timestamp,
        double lat,
        double lng,
        double heading,
        double speedKmh,
        Double fuelLevelPercent,
        Double engineTemperatureC,
        double batteryLevelPercent,
        Double odometerKm,
        Integer rpm,
        boolean engineOn,
        String connectivity) {
}
