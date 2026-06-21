package com.guardiants.platform.commands.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.commands.domain.model.entities.DeviceHealth;
import com.guardiants.platform.commands.domain.repositories.DeviceHealthRepository;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.assemblers.DeviceHealthEntityAssembler;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.repositories.DeviceHealthPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DeviceHealthRepositoryImpl implements DeviceHealthRepository {

    private final DeviceHealthPersistenceRepository persistenceRepository;
    private final DeviceHealthEntityAssembler assembler;

    public DeviceHealthRepositoryImpl(DeviceHealthPersistenceRepository persistenceRepository,
                                      DeviceHealthEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<DeviceHealth> findByVehicleId(Long vehicleId) {
        return persistenceRepository.findByVehicleId(vehicleId)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public DeviceHealth save(DeviceHealth health) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(health)));
    }
}
