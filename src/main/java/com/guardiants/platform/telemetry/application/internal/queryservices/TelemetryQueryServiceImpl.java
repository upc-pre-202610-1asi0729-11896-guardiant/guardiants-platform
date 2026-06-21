package com.guardiants.platform.telemetry.application.internal.queryservices;

import com.guardiants.platform.telemetry.application.queryservices.TelemetryQueryService;
import com.guardiants.platform.telemetry.domain.model.aggregates.TelemetryPoint;
import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import com.guardiants.platform.telemetry.domain.model.queries.GetLatestTelemetryPointQuery;
import com.guardiants.platform.telemetry.domain.model.queries.GetVehicleGeneralStatusQuery;
import com.guardiants.platform.telemetry.domain.repositories.TelemetryPointRepository;
import com.guardiants.platform.telemetry.domain.repositories.VehicleGeneralStatusRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TelemetryQueryServiceImpl implements TelemetryQueryService {

    private final VehicleGeneralStatusRepository vehicleGeneralStatusRepository;
    private final TelemetryPointRepository telemetryPointRepository;

    public TelemetryQueryServiceImpl(VehicleGeneralStatusRepository vehicleGeneralStatusRepository,
                                     TelemetryPointRepository telemetryPointRepository) {
        this.vehicleGeneralStatusRepository = vehicleGeneralStatusRepository;
        this.telemetryPointRepository = telemetryPointRepository;
    }

    @Override
    public Optional<VehicleGeneralStatus> handle(GetVehicleGeneralStatusQuery query) {
        return vehicleGeneralStatusRepository.findByVehicleId(query.vehicleId());
    }

    @Override
    public Optional<TelemetryPoint> handle(GetLatestTelemetryPointQuery query) {
        return telemetryPointRepository.findLatestByVehicleId(query.vehicleId());
    }
}
