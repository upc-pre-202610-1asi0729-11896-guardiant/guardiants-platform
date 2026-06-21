package com.guardiants.platform.telemetry.application.commandservices;

import com.guardiants.platform.telemetry.domain.model.aggregates.TelemetryPoint;
import com.guardiants.platform.telemetry.domain.model.commands.IngestTelemetryPointCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface TelemetryCommandService {
    Result<TelemetryPoint, TelemetryCommandFailure> handle(IngestTelemetryPointCommand command);
}
