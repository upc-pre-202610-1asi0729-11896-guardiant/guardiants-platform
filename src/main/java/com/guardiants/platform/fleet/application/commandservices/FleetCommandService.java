package com.guardiants.platform.fleet.application.commandservices;

import com.guardiants.platform.fleet.domain.model.aggregates.Fleet;
import com.guardiants.platform.fleet.domain.model.commands.CreateFleetCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface FleetCommandService {
    Result<Fleet, FleetCommandFailure> handle(CreateFleetCommand command);
}