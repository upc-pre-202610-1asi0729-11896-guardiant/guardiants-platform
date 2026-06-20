package com.guardiants.platform.fleet.domain.repositories;

import com.guardiants.platform.fleet.domain.model.entities.DeviceAssignment;

import java.util.Optional;

public interface DeviceAssignmentRepository {
    Optional<DeviceAssignment> findById(Long id);
    Optional<DeviceAssignment> findActiveByVehicleId(Long vehicleId);
    boolean existsActiveByDeviceSerial(String deviceSerial);
    Optional<Long> findVehicleIdByDeviceSerial(String deviceSerial);
    DeviceAssignment save(DeviceAssignment assignment);
}
