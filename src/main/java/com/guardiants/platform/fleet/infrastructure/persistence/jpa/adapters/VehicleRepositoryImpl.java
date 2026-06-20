package com.guardiants.platform.fleet.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.fleet.domain.model.aggregates.Vehicle;
import com.guardiants.platform.fleet.domain.repositories.VehicleRepository;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.assemblers.VehicleEntityAssembler;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.repositories.VehiclePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository {

    private final VehiclePersistenceRepository persistenceRepository;
    private final VehicleEntityAssembler assembler;

    public VehicleRepositoryImpl(VehiclePersistenceRepository persistenceRepository,
                                 VehicleEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public List<Vehicle> findAllByFleetId(Long fleetId) {
        return persistenceRepository.findAllByFleetId(fleetId).stream()
                .map(assembler::toDomainFromPersistenceEntity).toList();
    }

    @Override
    public Optional<Vehicle> findByPlate(String plate) {
        return persistenceRepository.findByPlate(plate).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByPlate(String plate) {
        return persistenceRepository.existsByPlate(plate);
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(vehicle)));
    }
}
