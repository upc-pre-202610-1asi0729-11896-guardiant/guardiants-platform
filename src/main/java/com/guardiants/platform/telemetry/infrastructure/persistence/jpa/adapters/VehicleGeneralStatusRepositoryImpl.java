package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import com.guardiants.platform.telemetry.domain.repositories.VehicleGeneralStatusRepository;
import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.assemblers.VehicleGeneralStatusEntityAssembler;
import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.repositories.VehicleGeneralStatusPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class VehicleGeneralStatusRepositoryImpl implements VehicleGeneralStatusRepository {

    private final VehicleGeneralStatusPersistenceRepository persistenceRepository;
    private final VehicleGeneralStatusEntityAssembler assembler;

    public VehicleGeneralStatusRepositoryImpl(
            VehicleGeneralStatusPersistenceRepository persistenceRepository,
            VehicleGeneralStatusEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<VehicleGeneralStatus> findByVehicleId(Long vehicleId) {
        return persistenceRepository.findByVehicleId(vehicleId)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public VehicleGeneralStatus save(VehicleGeneralStatus status) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(status)));
    }
}
