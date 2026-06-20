package com.guardiants.platform.fleet.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.fleet.domain.model.aggregates.Fleet;
import com.guardiants.platform.fleet.domain.repositories.FleetRepository;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.assemblers.FleetEntityAssembler;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.repositories.FleetPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FleetRepositoryImpl implements FleetRepository {

    private final FleetPersistenceRepository persistenceRepository;
    private final FleetEntityAssembler assembler;

    public FleetRepositoryImpl(FleetPersistenceRepository persistenceRepository,
                               FleetEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<Fleet> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public List<Fleet> findAllByOwnerId(Long ownerId) {
        return persistenceRepository.findAllByOwnerId(ownerId).stream()
                .map(assembler::toDomainFromPersistenceEntity).toList();
    }

    @Override
    public Fleet save(Fleet fleet) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(fleet)));
    }
}
