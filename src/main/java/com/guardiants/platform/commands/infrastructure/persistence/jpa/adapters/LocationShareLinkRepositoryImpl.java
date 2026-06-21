package com.guardiants.platform.commands.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.commands.domain.model.aggregates.LocationShareLink;
import com.guardiants.platform.commands.domain.repositories.LocationShareLinkRepository;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.assemblers.LocationShareLinkEntityAssembler;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.repositories.LocationShareLinkPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LocationShareLinkRepositoryImpl implements LocationShareLinkRepository {

    private final LocationShareLinkPersistenceRepository persistenceRepository;
    private final LocationShareLinkEntityAssembler assembler;

    public LocationShareLinkRepositoryImpl(LocationShareLinkPersistenceRepository persistenceRepository,
                                           LocationShareLinkEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<LocationShareLink> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public LocationShareLink save(LocationShareLink link) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(link)));
    }
}
