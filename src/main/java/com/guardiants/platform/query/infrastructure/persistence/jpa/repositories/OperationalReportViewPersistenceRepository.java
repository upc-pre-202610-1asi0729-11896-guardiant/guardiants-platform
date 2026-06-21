package com.guardiants.platform.query.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.OperationalReportViewPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface OperationalReportViewPersistenceRepository
        extends JpaRepository<OperationalReportViewPersistenceEntity, Long> {
    Optional<OperationalReportViewPersistenceEntity>
            findByFleetIdAndPeriodStartGreaterThanEqualAndPeriodEndLessThanEqual(
                    Long fleetId, Instant start, Instant end);
}
