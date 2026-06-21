package com.guardiants.platform.commands.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.commands.domain.model.aggregates.Command;
import com.guardiants.platform.commands.domain.repositories.CommandRepository;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.assemblers.CommandEntityAssembler;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.repositories.CommandPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommandRepositoryImpl implements CommandRepository {

    private final CommandPersistenceRepository persistenceRepository;
    private final CommandEntityAssembler assembler;

    public CommandRepositoryImpl(CommandPersistenceRepository persistenceRepository,
                                 CommandEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<Command> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public List<Command> findAllByVehicleId(Long vehicleId) {
        return persistenceRepository.findAllByVehicleId(vehicleId).stream()
                .map(assembler::toDomainFromPersistenceEntity).toList();
    }

    @Override
    public Command save(Command command) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(command)));
    }
}
