package com.guardiants.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.fleet.infrastructure.persistence.jpa.entities.AlertRulePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRulePersistenceRepository
        extends JpaRepository<AlertRulePersistenceEntity, Long> {
    List<AlertRulePersistenceEntity> findAllByFleetId(Long fleetId);
    List<AlertRulePersistenceEntity> findAllByVehicleIdAndEnabledTrue(Long vehicleId);
}
