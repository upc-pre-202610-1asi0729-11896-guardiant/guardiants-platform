package com.guardiants.platform.telemetry.interfaces.rest.transform;

import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import com.guardiants.platform.telemetry.interfaces.rest.resources.TelemetryPointResource;
import com.guardiants.platform.telemetry.interfaces.rest.resources.VehicleGeneralStatusResource;

public class VehicleGeneralStatusResourceFromEntityAssembler {

    public static VehicleGeneralStatusResource toResourceFromEntity(
            VehicleGeneralStatus status, TelemetryPointResource latestPoint) {
        return new VehicleGeneralStatusResource(
                status.getVehicleId(),
                latestPoint,
                status.getActiveAlertCount(),
                status.isLocked(),
                status.getLastUpdatedAt());
    }
}
