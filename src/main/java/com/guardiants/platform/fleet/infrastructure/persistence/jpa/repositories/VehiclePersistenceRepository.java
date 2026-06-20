package com.guardiants.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.fleet.infrastructure.persistence.jpa.entities.VehiclePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiclePersistenceRepository
        extends JpaRepository<VehiclePersistenceEntity, Long> {
    List<VehiclePersistenceEntity> findAllByFleetId(Long fleetId);
    Optional<VehiclePersistenceEntity> findByPlate(String plate);
    boolean existsByPlate(String plate);
}
