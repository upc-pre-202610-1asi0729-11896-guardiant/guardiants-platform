package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.entities.RouteSegmentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RouteSegmentPersistenceRepository
        extends JpaRepository<RouteSegmentPersistenceEntity, Long> {
    Optional<RouteSegmentPersistenceEntity> findFirstByVehicleIdAndEndedAtIsNull(Long vehicleId);
    List<RouteSegmentPersistenceEntity> findAllByVehicleIdAndStartedAtBetween(
            Long vehicleId, Instant start, Instant end);
}
