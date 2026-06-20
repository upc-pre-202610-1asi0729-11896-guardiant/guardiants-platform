package com.guardiants.platform.fleet.interfaces.rest.transform;

import com.guardiants.platform.fleet.domain.model.commands.RegisterVehicleCommand;
import com.guardiants.platform.fleet.interfaces.rest.resources.RegisterVehicleResource;

public class RegisterVehicleCommandFromResourceAssembler {

    public static RegisterVehicleCommand toCommandFromResource(RegisterVehicleResource resource) {
        return new RegisterVehicleCommand(resource.fleetId(), resource.plate(),
                resource.model(), resource.brand(), resource.year());
    }
}
