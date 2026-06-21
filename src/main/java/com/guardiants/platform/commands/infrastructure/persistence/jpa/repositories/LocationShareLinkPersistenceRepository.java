package com.guardiants.platform.commands.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.commands.infrastructure.persistence.jpa.entities.LocationShareLinkPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationShareLinkPersistenceRepository
        extends JpaRepository<LocationShareLinkPersistenceEntity, Long> {
}
