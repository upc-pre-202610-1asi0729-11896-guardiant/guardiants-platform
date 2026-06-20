package com.guardiants.platform.fleet.application.commandservices;

import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.fleet.domain.model.commands.ApproveVehicleLoanCommand;
import com.guardiants.platform.fleet.domain.model.commands.ConfirmVehicleReturnCommand;
import com.guardiants.platform.fleet.domain.model.commands.RejectVehicleLoanCommand;
import com.guardiants.platform.fleet.domain.model.commands.RequestVehicleLoanCommand;
import com.guardiants.platform.fleet.domain.model.commands.RequestVehicleReturnCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface VehicleLoanCommandService {
    Result<VehicleLoan, VehicleLoanCommandFailure> handle(RequestVehicleLoanCommand command);
    Result<VehicleLoan, VehicleLoanCommandFailure> handle(ApproveVehicleLoanCommand command);
    Result<VehicleLoan, VehicleLoanCommandFailure> handle(RejectVehicleLoanCommand command);
    Result<VehicleLoan, VehicleLoanCommandFailure> handle(RequestVehicleReturnCommand command);
    Result<VehicleLoan, VehicleLoanCommandFailure> handle(ConfirmVehicleReturnCommand command);
}
