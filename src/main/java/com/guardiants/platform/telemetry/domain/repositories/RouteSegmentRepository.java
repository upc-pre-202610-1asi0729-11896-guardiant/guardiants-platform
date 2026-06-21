package com.guardiants.platform.telemetry.domain.repositories;

import com.guardiants.platform.telemetry.domain.model.aggregates.RouteSegment;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RouteSegmentRepository {
    Optional<RouteSegment> findById(Long id);
    Optional<RouteSegment> findOngoingByVehicleId(Long vehicleId);
    List<RouteSegment> findAllByVehicleIdAndPeriod(Long vehicleId, Instant start, Instant end);
    RouteSegment save(RouteSegment segment);
}
