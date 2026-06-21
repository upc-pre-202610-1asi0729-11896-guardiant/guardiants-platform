package com.guardiants.platform.telemetry.domain.repositories;

import com.guardiants.platform.telemetry.domain.model.aggregates.TelemetryPoint;
import java.util.Optional;

public interface TelemetryPointRepository {
    Optional<TelemetryPoint> findById(Long id);
    TelemetryPoint save(TelemetryPoint point);
}
