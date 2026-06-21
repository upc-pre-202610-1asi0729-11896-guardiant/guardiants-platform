package com.guardiants.platform.query.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.DrivingReportViewPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface DrivingReportViewPersistenceRepository
        extends JpaRepository<DrivingReportViewPersistenceEntity, Long> {
    Optional<DrivingReportViewPersistenceEntity>
            findByVehicleIdAndPeriodStartGreaterThanEqualAndPeriodEndLessThanEqual(
                    Long vehicleId, Instant start, Instant end);
}
