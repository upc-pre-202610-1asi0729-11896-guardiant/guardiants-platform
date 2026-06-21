package com.guardiants.platform.telemetry.interfaces.rest.transform;

import com.guardiants.platform.telemetry.domain.model.commands.IngestTelemetryPointCommand;
import com.guardiants.platform.telemetry.domain.model.valueobjects.*;
import com.guardiants.platform.telemetry.interfaces.rest.resources.IngestTelemetryPointResource;

public class IngestTelemetryPointCommandFromResourceAssembler {

    public static IngestTelemetryPointCommand toCommandFromResource(
            IngestTelemetryPointResource resource) {
        return new IngestTelemetryPointCommand(
                resource.deviceSerial(),
                resource.vehicleId(),
                resource.timestamp(),
                new GeoPoint(resource.lat(), resource.lng()),
                new VehicleStatusSnapshot(
                        resource.speedKmh(), resource.fuelLevelPercent(),
                        resource.engineTemperatureC(), resource.batteryLevelPercent(),
                        resource.odometerKm(), resource.rpm(), resource.engineOn()),
                ConnectivityValue.valueOf(resource.connectivity()));
    }
}
