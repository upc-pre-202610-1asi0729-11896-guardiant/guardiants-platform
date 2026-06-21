package com.guardiants.platform.query.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.query.domain.model.readmodels.ReportExport;
import com.guardiants.platform.query.domain.repositories.ReportExportRepository;
import com.guardiants.platform.query.infrastructure.persistence.jpa.assemblers.ReportExportEntityAssembler;
import com.guardiants.platform.query.infrastructure.persistence.jpa.repositories.ReportExportPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class ReportExportRepositoryImpl implements ReportExportRepository {

    private final ReportExportPersistenceRepository persistenceRepository;
    private final ReportExportEntityAssembler assembler;

    public ReportExportRepositoryImpl(ReportExportPersistenceRepository persistenceRepository,
                                      ReportExportEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<ReportExport> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public ReportExport save(ReportExport export) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(export)));
    }
}
