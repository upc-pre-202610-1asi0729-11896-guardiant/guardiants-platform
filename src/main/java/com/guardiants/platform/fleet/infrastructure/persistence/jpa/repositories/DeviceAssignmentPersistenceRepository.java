package com.guardiants.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.fleet.domain.model.entities.DeviceAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceAssignmentPersistenceRepository
        extends JpaRepository<DeviceAssignment, Long> {
    Optional<DeviceAssignment> findFirstByVehicleIdAndUnassignedAtIsNull(Long vehicleId);
    boolean existsByDeviceSerialAndUnassignedAtIsNull(String deviceSerial);
    Optional<DeviceAssignment> findFirstByDeviceSerialAndUnassignedAtIsNull(String deviceSerial);
}
