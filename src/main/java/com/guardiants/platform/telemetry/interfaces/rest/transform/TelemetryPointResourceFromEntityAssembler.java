package com.guardiants.platform.telemetry.interfaces.rest.transform;

import com.guardiants.platform.telemetry.domain.model.aggregates.TelemetryPoint;
import com.guardiants.platform.telemetry.interfaces.rest.resources.TelemetryPointResource;

public class TelemetryPointResourceFromEntityAssembler {

    public static TelemetryPointResource toResourceFromEntity(TelemetryPoint point) {
        return new TelemetryPointResource(
                point.getId(),
                point.getVehicleId(),
                point.getDeviceSerial(),
                point.getTimestamp(),
                point.getLocation().lat(),
                point.getLocation().lng(),
                point.getVehicleStatus().speedKmh(),
                point.getVehicleStatus().fuelLevelPercent(),
                point.getVehicleStatus().engineTemperatureC(),
                point.getVehicleStatus().batteryLevelPercent(),
                point.getConnectivity().getValue().name());
    }
}
