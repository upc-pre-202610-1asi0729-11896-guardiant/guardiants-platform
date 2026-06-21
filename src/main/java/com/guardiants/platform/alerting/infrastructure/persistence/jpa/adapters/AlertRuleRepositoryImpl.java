package com.guardiants.platform.alerting.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.alerting.domain.model.aggregates.AlertRule;
import com.guardiants.platform.alerting.domain.repositories.AlertRuleRepository;
import com.guardiants.platform.alerting.infrastructure.persistence.jpa.assemblers.AlertRuleEntityAssembler;
import com.guardiants.platform.alerting.infrastructure.persistence.jpa.repositories.AlertingAlertRulePersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("alertingAlertRuleRepositoryImpl")
public class AlertRuleRepositoryImpl implements AlertRuleRepository {

    private final AlertingAlertRulePersistenceRepository persistenceRepository;
    private final AlertRuleEntityAssembler assembler;

    public AlertRuleRepositoryImpl(AlertingAlertRulePersistenceRepository persistenceRepository,
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
    public List<AlertRule> findAllByOwnerId(Long ownerId) {
        return persistenceRepository.findAllByOwnerId(ownerId)
                .stream()
                .map(assembler::toDomainFromPersistenceEntity)
                .toList();
    }

    @Override
    public AlertRule save(AlertRule rule) {
        var entity = assembler.toPersistenceEntityFromDomain(rule);
        var saved = persistenceRepository.save(entity);
        return assembler.toDomainFromPersistenceEntity(saved);
    }
}
