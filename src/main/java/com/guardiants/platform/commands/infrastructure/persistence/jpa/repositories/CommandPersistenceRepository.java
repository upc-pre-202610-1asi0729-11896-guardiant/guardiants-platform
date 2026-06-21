package com.guardiants.platform.commands.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.commands.infrastructure.persistence.jpa.entities.CommandPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandPersistenceRepository
        extends JpaRepository<CommandPersistenceEntity, Long> {
    List<CommandPersistenceEntity> findAllByVehicleId(Long vehicleId);
}
