package com.guardiants.platform.telemetry.interfaces.rest.resources;

import java.time.Instant;

public record VehicleGeneralStatusResource(
        Long vehicleId,
        TelemetryPointResource latestPoint,
        int activeAlertCount,
        boolean isLocked,
        Instant lastUpdatedAt) {
}
