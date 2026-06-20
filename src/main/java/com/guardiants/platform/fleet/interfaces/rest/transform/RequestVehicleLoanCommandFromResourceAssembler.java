package com.guardiants.platform.fleet.interfaces.rest.transform;

import com.guardiants.platform.fleet.domain.model.commands.RequestVehicleLoanCommand;
import com.guardiants.platform.fleet.interfaces.rest.resources.RequestVehicleLoanResource;

public class RequestVehicleLoanCommandFromResourceAssembler {

    public static RequestVehicleLoanCommand toCommandFromResource(RequestVehicleLoanResource resource) {
        return new RequestVehicleLoanCommand(resource.vehicleId(),
                resource.requestedByPersonnelId(), resource.expectedReturnDate());
    }
}
