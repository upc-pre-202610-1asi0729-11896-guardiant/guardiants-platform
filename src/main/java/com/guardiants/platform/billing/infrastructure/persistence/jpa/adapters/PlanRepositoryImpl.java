package com.guardiants.platform.billing.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.billing.domain.model.aggregates.Plan;
import com.guardiants.platform.billing.domain.repositories.PlanRepository;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.assemblers.PlanEntityAssembler;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.repositories.PlanPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanPersistenceRepository persistenceRepository;
    private final PlanEntityAssembler assembler;

    public PlanRepositoryImpl(PlanPersistenceRepository persistenceRepository,
                              PlanEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<Plan> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public List<Plan> findAll() {
        return persistenceRepository.findAll().stream()
                .map(assembler::toDomainFromPersistenceEntity).toList();
    }

    @Override
    public Plan save(Plan plan) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(plan)));
    }
}