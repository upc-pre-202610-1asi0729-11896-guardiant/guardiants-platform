package com.guardiants.platform.telemetry.domain.model.commands;

import com.guardiants.platform.telemetry.domain.model.valueobjects.ConnectivityValue;
import com.guardiants.platform.telemetry.domain.model.valueobjects.GeoPoint;
import com.guardiants.platform.telemetry.domain.model.valueobjects.VehicleStatusSnapshot;
import java.time.Instant;

public record IngestTelemetryPointCommand(
        String deviceSerial,
        Long vehicleId,
        Instant timestamp,
        GeoPoint location,
        VehicleStatusSnapshot vehicleStatus,
        ConnectivityValue connectivity) {
}
