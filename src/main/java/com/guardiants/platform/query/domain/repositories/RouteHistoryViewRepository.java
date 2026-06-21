package com.guardiants.platform.query.domain.repositories;

import com.guardiants.platform.query.domain.model.readmodels.RouteHistoryView;
import java.time.Instant;
import java.util.Optional;

public interface RouteHistoryViewRepository {
    Optional<RouteHistoryView> findById(Long id);
    Optional<RouteHistoryView> findByVehicleIdAndPeriod(Long vehicleId, Instant start, Instant end);
    RouteHistoryView save(RouteHistoryView view);
}
