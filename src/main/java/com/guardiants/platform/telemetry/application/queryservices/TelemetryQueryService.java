package com.guardiants.platform.telemetry.application.queryservices;

import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import com.guardiants.platform.telemetry.domain.model.queries.GetVehicleGeneralStatusQuery;
import java.util.Optional;

public interface TelemetryQueryService {
    Optional<VehicleGeneralStatus> handle(GetVehicleGeneralStatusQuery query);
}
