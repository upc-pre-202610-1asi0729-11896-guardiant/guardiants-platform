package com.guardiants.platform.query.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.query.domain.model.readmodels.DrivingReportView;
import com.guardiants.platform.query.domain.repositories.DrivingReportViewRepository;
import com.guardiants.platform.query.infrastructure.persistence.jpa.assemblers.DrivingReportViewEntityAssembler;
import com.guardiants.platform.query.infrastructure.persistence.jpa.repositories.DrivingReportViewPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public class DrivingReportViewRepositoryImpl implements DrivingReportViewRepository {

    private final DrivingReportViewPersistenceRepository persistenceRepository;
    private final DrivingReportViewEntityAssembler assembler;

    public DrivingReportViewRepositoryImpl(DrivingReportViewPersistenceRepository persistenceRepository,
                                           DrivingReportViewEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<DrivingReportView> findByVehicleIdAndPeriod(Long vehicleId, Instant start, Instant end) {
        return persistenceRepository
                .findByVehicleIdAndPeriodStartGreaterThanEqualAndPeriodEndLessThanEqual(vehicleId, start, end)
                .map(assembler::toViewFromPersistenceEntity);
    }

    @Override
    public DrivingReportView save(DrivingReportView view) {
        return assembler.toViewFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromView(view)));
    }
}
