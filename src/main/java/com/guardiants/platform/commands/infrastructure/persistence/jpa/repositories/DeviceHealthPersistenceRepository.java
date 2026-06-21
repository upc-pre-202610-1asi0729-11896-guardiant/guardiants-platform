package com.guardiants.platform.commands.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.commands.infrastructure.persistence.jpa.entities.DeviceHealthPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceHealthPersistenceRepository
        extends JpaRepository<DeviceHealthPersistenceEntity, Long> {
    Optional<DeviceHealthPersistenceEntity> findByVehicleId(Long vehicleId);
}
