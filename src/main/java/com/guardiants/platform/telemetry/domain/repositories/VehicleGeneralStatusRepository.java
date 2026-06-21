package com.guardiants.platform.telemetry.domain.repositories;

import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import java.util.Optional;

public interface VehicleGeneralStatusRepository {
    Optional<VehicleGeneralStatus> findByVehicleId(Long vehicleId);
    VehicleGeneralStatus save(VehicleGeneralStatus status);
}
