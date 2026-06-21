package com.guardiants.platform.alerting.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import com.guardiants.platform.alerting.domain.repositories.SecurityAlertRepository;
import com.guardiants.platform.alerting.infrastructure.persistence.jpa.assemblers.SecurityAlertEntityAssembler;
import com.guardiants.platform.alerting.infrastructure.persistence.jpa.repositories.SecurityAlertPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class SecurityAlertRepositoryImpl implements SecurityAlertRepository {

    private final SecurityAlertPersistenceRepository persistenceRepository;
    private final SecurityAlertEntityAssembler assembler;

    public SecurityAlertRepositoryImpl(SecurityAlertPersistenceRepository persistenceRepository,
                                       SecurityAlertEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<SecurityAlert> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public SecurityAlert save(SecurityAlert alert) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(alert)));
    }
}
