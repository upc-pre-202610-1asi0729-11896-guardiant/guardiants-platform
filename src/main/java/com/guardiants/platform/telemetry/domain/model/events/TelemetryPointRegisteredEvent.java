package com.guardiants.platform.telemetry.domain.model.events;

import com.guardiants.platform.telemetry.domain.model.valueobjects.ConnectivityValue;
import com.guardiants.platform.telemetry.domain.model.valueobjects.GeoPoint;
import com.guardiants.platform.telemetry.domain.model.valueobjects.VehicleStatusSnapshot;
import java.time.Instant;

public record TelemetryPointRegisteredEvent(
        Long telemetryPointId,
        Long vehicleId,
        GeoPoint location,
        VehicleStatusSnapshot vehicleStatus,
        ConnectivityValue connectivity,
        Instant occurredAt) {
}
