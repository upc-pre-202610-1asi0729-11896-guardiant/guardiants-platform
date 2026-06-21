package com.guardiants.platform.telemetry.domain.model.commands;

import com.guardiants.platform.telemetry.domain.model.valueobjects.ConnectivityValue;
import java.time.Instant;

public record UpdateVehicleConnectivityCommand(
        Long vehicleId,
        ConnectivityValue connectivity,
        Instant occurredAt) {
}
