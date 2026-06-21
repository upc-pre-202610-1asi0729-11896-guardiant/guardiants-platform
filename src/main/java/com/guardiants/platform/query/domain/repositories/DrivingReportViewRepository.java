package com.guardiants.platform.query.domain.repositories;

import com.guardiants.platform.query.domain.model.readmodels.DrivingReportView;
import java.time.Instant;
import java.util.Optional;

public interface DrivingReportViewRepository {
    Optional<DrivingReportView> findByVehicleIdAndPeriod(Long vehicleId, Instant start, Instant end);
    DrivingReportView save(DrivingReportView view);
}
