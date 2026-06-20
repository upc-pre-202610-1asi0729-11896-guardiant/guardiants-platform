package com.guardiants.platform.fleet.interfaces.rest.transform;

import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.fleet.interfaces.rest.resources.VehicleLoanResource;

public class VehicleLoanResourceFromEntityAssembler {

    public static VehicleLoanResource toResourceFromEntity(VehicleLoan loan) {
        return new VehicleLoanResource(loan.getId(), loan.getVehicleId(), loan.getFleetId(),
                loan.getRequestedByPersonnelId(), loan.getApprovedByApproverId(),
                loan.getStatus().name(), loan.getRequestedAt(), loan.getDecidedAt(),
                loan.getAssignedAt(), loan.getReturnRequestedAt(), loan.getReturnConfirmedAt(),
                loan.getRejectionReason(), loan.getExpectedReturnDate());
    }
}
