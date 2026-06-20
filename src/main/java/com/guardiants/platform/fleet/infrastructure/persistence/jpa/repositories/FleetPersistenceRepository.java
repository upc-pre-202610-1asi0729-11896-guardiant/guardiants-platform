package com.guardiants.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.fleet.infrastructure.persistence.jpa.entities.FleetPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FleetPersistenceRepository
        extends JpaRepository<FleetPersistenceEntity, Long> {
    List<FleetPersistenceEntity> findAllByOwnerId(Long ownerId);
}
