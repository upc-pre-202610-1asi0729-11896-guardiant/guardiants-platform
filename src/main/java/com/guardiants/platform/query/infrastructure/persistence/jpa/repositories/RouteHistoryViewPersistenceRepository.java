package com.guardiants.platform.query.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.RouteHistoryViewPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RouteHistoryViewPersistenceRepository
        extends JpaRepository<RouteHistoryViewPersistenceEntity, Long> {
    Optional<RouteHistoryViewPersistenceEntity>
            findByVehicleIdAndPeriodStartGreaterThanEqualAndPeriodEndLessThanEqual(
                    Long vehicleId, Instant start, Instant end);
}
