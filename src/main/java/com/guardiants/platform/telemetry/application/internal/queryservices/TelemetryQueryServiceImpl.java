package com.guardiants.platform.telemetry.application.internal.queryservices;

import com.guardiants.platform.telemetry.application.queryservices.TelemetryQueryService;
import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import com.guardiants.platform.telemetry.domain.model.queries.GetVehicleGeneralStatusQuery;
import com.guardiants.platform.telemetry.domain.repositories.VehicleGeneralStatusRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TelemetryQueryServiceImpl implements TelemetryQueryService {

    private final VehicleGeneralStatusRepository vehicleGeneralStatusRepository;

    public TelemetryQueryServiceImpl(VehicleGeneralStatusRepository vehicleGeneralStatusRepository) {
        this.vehicleGeneralStatusRepository = vehicleGeneralStatusRepository;
    }

    @Override
    public Optional<VehicleGeneralStatus> handle(GetVehicleGeneralStatusQuery query) {
        return vehicleGeneralStatusRepository.findByVehicleId(query.vehicleId());
    }
}
