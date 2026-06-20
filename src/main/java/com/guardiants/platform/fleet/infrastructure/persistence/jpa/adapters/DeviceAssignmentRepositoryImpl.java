package com.guardiants.platform.fleet.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.fleet.domain.model.entities.DeviceAssignment;
import com.guardiants.platform.fleet.domain.repositories.DeviceAssignmentRepository;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.repositories.DeviceAssignmentPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DeviceAssignmentRepositoryImpl implements DeviceAssignmentRepository {

    private final DeviceAssignmentPersistenceRepository persistenceRepository;

    public DeviceAssignmentRepositoryImpl(DeviceAssignmentPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Optional<DeviceAssignment> findById(Long id) {
        return persistenceRepository.findById(id);
    }

    @Override
    public Optional<DeviceAssignment> findActiveByVehicleId(Long vehicleId) {
        return persistenceRepository.findFirstByVehicleIdAndUnassignedAtIsNull(vehicleId);
    }

    @Override
    public boolean existsActiveByDeviceSerial(String deviceSerial) {
        return persistenceRepository.existsByDeviceSerialAndUnassignedAtIsNull(deviceSerial);
    }

    @Override
    public Optional<Long> findVehicleIdByDeviceSerial(String deviceSerial) {
        return persistenceRepository.findFirstByDeviceSerialAndUnassignedAtIsNull(deviceSerial)
                .map(DeviceAssignment::getVehicleId);
    }

    @Override
    public DeviceAssignment save(DeviceAssignment assignment) {
        return persistenceRepository.save(assignment);
    }
}
