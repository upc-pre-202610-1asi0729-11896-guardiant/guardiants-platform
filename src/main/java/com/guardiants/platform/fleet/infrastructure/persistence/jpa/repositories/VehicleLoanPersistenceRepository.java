package com.guardiants.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.fleet.infrastructure.persistence.jpa.entities.VehicleLoanPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleLoanPersistenceRepository
        extends JpaRepository<VehicleLoanPersistenceEntity, Long> {
    List<VehicleLoanPersistenceEntity> findAllByFleetId(Long fleetId);
    List<VehicleLoanPersistenceEntity> findAllByFleetIdAndStatus(Long fleetId, String status);
    Optional<VehicleLoanPersistenceEntity>
            findFirstByVehicleIdAndStatusIn(Long vehicleId, List<String> statuses);
}
