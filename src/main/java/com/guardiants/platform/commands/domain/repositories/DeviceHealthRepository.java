package com.guardiants.platform.commands.domain.repositories;

import com.guardiants.platform.commands.domain.model.entities.DeviceHealth;
import java.util.Optional;

public interface DeviceHealthRepository {
    Optional<DeviceHealth> findByVehicleId(Long vehicleId);
    DeviceHealth save(DeviceHealth health);
}
