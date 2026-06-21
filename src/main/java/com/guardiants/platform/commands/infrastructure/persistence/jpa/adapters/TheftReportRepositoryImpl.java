package com.guardiants.platform.commands.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.commands.domain.model.aggregates.TheftReport;
import com.guardiants.platform.commands.domain.model.valueobjects.IncidentStatus;
import com.guardiants.platform.commands.domain.repositories.TheftReportRepository;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.assemblers.TheftReportEntityAssembler;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.repositories.TheftReportPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TheftReportRepositoryImpl implements TheftReportRepository {

    private final TheftReportPersistenceRepository persistenceRepository;
    private final TheftReportEntityAssembler assembler;

    public TheftReportRepositoryImpl(TheftReportPersistenceRepository persistenceRepository,
                                     TheftReportEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<TheftReport> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public List<TheftReport> findAllActive() {
        return persistenceRepository.findAllByStatus(IncidentStatus.ACTIVE.name()).stream()
                .map(assembler::toDomainFromPersistenceEntity).toList();
    }

    @Override
    public TheftReport save(TheftReport report) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(report)));
    }
}
