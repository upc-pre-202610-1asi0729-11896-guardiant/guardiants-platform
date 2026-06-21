package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.entities.TelemetryPointPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetryPointPersistenceRepository
        extends JpaRepository<TelemetryPointPersistenceEntity, Long> {
}
