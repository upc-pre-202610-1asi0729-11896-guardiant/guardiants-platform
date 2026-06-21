package com.guardiants.platform.telemetry.infrastructure.mqtt.emqx;

/**
 * Maps the JSON payload received from an MQTT telemetry topic to Java fields.
 * Mirrors {@code IngestTelemetryPointResource}.
 */
public record TelemetryPayloadDto(
        String deviceSerial,
        Long vehicleId,
        String timestamp,
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
