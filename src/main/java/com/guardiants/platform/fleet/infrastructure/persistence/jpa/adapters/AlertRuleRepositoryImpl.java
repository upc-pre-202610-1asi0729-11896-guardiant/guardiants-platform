package com.guardiants.platform.fleet.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.fleet.domain.model.aggregates.AlertRule;
import com.guardiants.platform.fleet.domain.repositories.AlertRuleRepository;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.assemblers.AlertRuleEntityAssembler;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.repositories.AlertRulePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public List<AlertRule> findAllByFleetId(Long fleetId) {
        return persistenceRepository.findAllByFleetId(fleetId).stream()
                .map(assembler::toDomainFromPersistenceEntity).toList();
    }

    @Override
    public List<AlertRule> findAllActiveByVehicleId(Long vehicleId) {
        return persistenceRepository.findAllByVehicleIdAndEnabledTrue(vehicleId).stream()
                .map(assembler::toDomainFromPersistenceEntity).toList();
    }

    @Override
    public AlertRule save(AlertRule rule) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(rule)));
    }
}
