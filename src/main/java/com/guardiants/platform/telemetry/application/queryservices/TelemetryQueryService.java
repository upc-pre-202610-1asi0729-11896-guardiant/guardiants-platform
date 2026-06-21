package com.guardiants.platform.telemetry.application.queryservices;

import com.guardiants.platform.telemetry.domain.model.aggregates.RouteSegment;
import com.guardiants.platform.telemetry.domain.model.aggregates.TelemetryPoint;
import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import com.guardiants.platform.telemetry.domain.model.queries.GetLatestTelemetryPointQuery;
import com.guardiants.platform.telemetry.domain.model.queries.GetRouteHistoryQuery;
import com.guardiants.platform.telemetry.domain.model.queries.GetVehicleGeneralStatusQuery;
import java.util.List;
import java.util.Optional;

public interface TelemetryQueryService {
    Optional<VehicleGeneralStatus> handle(GetVehicleGeneralStatusQuery query);
    Optional<TelemetryPoint> handle(GetLatestTelemetryPointQuery query);
    List<RouteSegment> handle(GetRouteHistoryQuery query);
}
