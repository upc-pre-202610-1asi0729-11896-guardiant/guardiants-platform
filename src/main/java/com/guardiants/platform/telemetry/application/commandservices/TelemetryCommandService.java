package com.guardiants.platform.telemetry.application.commandservices;

import com.guardiants.platform.telemetry.domain.model.aggregates.TelemetryPoint;
import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import com.guardiants.platform.telemetry.domain.model.commands.IngestTelemetryPointCommand;
import com.guardiants.platform.telemetry.domain.model.commands.SetEngineLockStatusCommand;
import com.guardiants.platform.telemetry.domain.model.commands.UpdateVehicleConnectivityCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface TelemetryCommandService {
    Result<TelemetryPoint, TelemetryCommandFailure> handle(IngestTelemetryPointCommand command);
    Result<TelemetryPoint, TelemetryCommandFailure> handle(UpdateVehicleConnectivityCommand command);
    Result<VehicleGeneralStatus, TelemetryCommandFailure> handle(SetEngineLockStatusCommand command);
}
