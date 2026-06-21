package com.guardiants.platform.query.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.query.domain.model.readmodels.OperationalReportView;
import com.guardiants.platform.query.domain.repositories.OperationalReportViewRepository;
import com.guardiants.platform.query.infrastructure.persistence.jpa.assemblers.OperationalReportViewEntityAssembler;
import com.guardiants.platform.query.infrastructure.persistence.jpa.repositories.OperationalReportViewPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public class OperationalReportViewRepositoryImpl implements OperationalReportViewRepository {

    private final OperationalReportViewPersistenceRepository persistenceRepository;
    private final OperationalReportViewEntityAssembler assembler;

    public OperationalReportViewRepositoryImpl(
            OperationalReportViewPersistenceRepository persistenceRepository,
            OperationalReportViewEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<OperationalReportView> findByFleetIdAndPeriod(Long fleetId, Instant start, Instant end) {
        return persistenceRepository
                .findByFleetIdAndPeriodStartGreaterThanEqualAndPeriodEndLessThanEqual(fleetId, start, end)
                .map(assembler::toViewFromPersistenceEntity);
    }

    @Override
    public OperationalReportView save(OperationalReportView view) {
        return assembler.toViewFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromView(view)));
    }
}
