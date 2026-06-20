package com.guardiants.platform.fleet.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.fleet.domain.model.valueobjects.LoanStatus;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.entities.VehicleLoanPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleLoanEntityAssembler {

    public VehicleLoanPersistenceEntity toPersistenceEntityFromDomain(VehicleLoan loan) {
        var entity = new VehicleLoanPersistenceEntity();
        entity.setId(loan.getId());
        entity.setVehicleId(loan.getVehicleId());
        entity.setFleetId(loan.getFleetId());
        entity.setRequestedByPersonnelId(loan.getRequestedByPersonnelId());
        entity.setApprovedByApproverId(loan.getApprovedByApproverId());
        entity.setStatus(loan.getStatus().name());
        entity.setRequestedAt(loan.getRequestedAt());
        entity.setDecidedAt(loan.getDecidedAt());
        entity.setAssignedAt(loan.getAssignedAt());
        entity.setReturnRequestedAt(loan.getReturnRequestedAt());
        entity.setReturnConfirmedAt(loan.getReturnConfirmedAt());
        entity.setRejectionReason(loan.getRejectionReason());
        entity.setExpectedReturnDate(loan.getExpectedReturnDate());
        return entity;
    }

    public VehicleLoan toDomainFromPersistenceEntity(VehicleLoanPersistenceEntity entity) {
        var loan = new VehicleLoan(
                entity.getVehicleId(),
                entity.getFleetId(),
                entity.getRequestedByPersonnelId(),
                entity.getApprovedByApproverId(),
                LoanStatus.valueOf(entity.getStatus()),
                entity.getRequestedAt(),
                entity.getDecidedAt(),
                entity.getAssignedAt(),
                entity.getReturnRequestedAt(),
                entity.getReturnConfirmedAt(),
                entity.getRejectionReason(),
                entity.getExpectedReturnDate());
        loan.setId(entity.getId());
        return loan;
    }
}
