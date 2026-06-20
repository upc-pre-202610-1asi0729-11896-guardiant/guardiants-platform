package com.guardiants.platform.fleet.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.fleet.domain.model.valueobjects.LoanStatus;
import com.guardiants.platform.fleet.domain.repositories.VehicleLoanRepository;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.assemblers.VehicleLoanEntityAssembler;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.repositories.VehicleLoanPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VehicleLoanRepositoryImpl implements VehicleLoanRepository {

    private static final List<String> ACTIVE_STATUSES = List.of(
            LoanStatus.REQUESTED.name(), LoanStatus.APPROVED.name(),
            LoanStatus.ASSIGNED.name(), LoanStatus.RETURN_REQUESTED.name());

    private final VehicleLoanPersistenceRepository persistenceRepository;
    private final VehicleLoanEntityAssembler assembler;

    public VehicleLoanRepositoryImpl(VehicleLoanPersistenceRepository persistenceRepository,
                                     VehicleLoanEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<VehicleLoan> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public List<VehicleLoan> findAllByFleetId(Long fleetId) {
        return persistenceRepository.findAllByFleetId(fleetId).stream()
                .map(assembler::toDomainFromPersistenceEntity).toList();
    }

    @Override
    public List<VehicleLoan> findAllByFleetIdAndStatus(Long fleetId, LoanStatus status) {
        return persistenceRepository.findAllByFleetIdAndStatus(fleetId, status.name()).stream()
                .map(assembler::toDomainFromPersistenceEntity).toList();
    }

    @Override
    public Optional<VehicleLoan> findActiveByVehicleId(Long vehicleId) {
        return persistenceRepository.findFirstByVehicleIdAndStatusIn(vehicleId, ACTIVE_STATUSES)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public VehicleLoan save(VehicleLoan loan) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(loan)));
    }
}
