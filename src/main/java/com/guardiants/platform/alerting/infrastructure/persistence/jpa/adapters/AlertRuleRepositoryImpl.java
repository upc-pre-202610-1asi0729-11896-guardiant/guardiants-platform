package com.guardiants.platform.alerting.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.alerting.domain.model.aggregates.AlertRule;
import com.guardiants.platform.alerting.domain.repositories.AlertRuleRepository;
import com.guardiants.platform.alerting.infrastructure.persistence.jpa.assemblers.AlertRuleEntityAssembler;
import com.guardiants.platform.alerting.infrastructure.persistence.jpa.repositories.AlertRulePersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class AlertRuleRepositoryImpl implements AlertRuleRepository {

    private final AlertRulePersistenceRepository persistenceRepository;
    private final AlertRuleEntityAssembler assembler;

    public AlertRuleRepositoryImpl(AlertRulePersistenceRepository persistenceRepository,
                                   AlertRuleEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<AlertRule> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public AlertRule save(AlertRule rule) {
        var entity = assembler.toPersistenceEntityFromDomain(rule);
        var saved = persistenceRepository.save(entity);
        return assembler.toDomainFromPersistenceEntity(saved);
    }
}
