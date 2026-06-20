package com.guardiants.platform.fleet.application.commandservices;

import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.fleet.domain.model.commands.RequestVehicleLoanCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface VehicleLoanCommandService {
    Result<VehicleLoan, VehicleLoanCommandFailure> handle(RequestVehicleLoanCommand command);
}
