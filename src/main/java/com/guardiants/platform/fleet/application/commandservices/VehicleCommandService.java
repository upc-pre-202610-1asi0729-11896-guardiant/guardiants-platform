package com.guardiants.platform.fleet.application.commandservices;

import com.guardiants.platform.fleet.domain.model.aggregates.Vehicle;
import com.guardiants.platform.fleet.domain.model.commands.DeactivateVehicleCommand;
import com.guardiants.platform.fleet.domain.model.commands.RegisterVehicleCommand;
import com.guardiants.platform.fleet.domain.model.commands.UpdateVehicleCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface VehicleCommandService {
    Result<Vehicle, VehicleCommandFailure> handle(RegisterVehicleCommand command);
    Result<Vehicle, VehicleCommandFailure> handle(UpdateVehicleCommand command);
    Result<Vehicle, VehicleCommandFailure> handle(DeactivateVehicleCommand command);
}
