package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.entities.VehicleGeneralStatusPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleGeneralStatusPersistenceRepository
        extends JpaRepository<VehicleGeneralStatusPersistenceEntity, Long> {
    Optional<VehicleGeneralStatusPersistenceEntity> findByVehicleId(Long vehicleId);
}
