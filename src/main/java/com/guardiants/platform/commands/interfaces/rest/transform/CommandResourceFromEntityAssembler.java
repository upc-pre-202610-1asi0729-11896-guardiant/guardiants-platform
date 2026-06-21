package com.guardiants.platform.commands.interfaces.rest.transform;

import com.guardiants.platform.commands.domain.model.aggregates.Command;
import com.guardiants.platform.commands.interfaces.rest.resources.CommandResource;

public class CommandResourceFromEntityAssembler {

    public static CommandResource toResourceFromEntity(Command command) {
        return new CommandResource(command.getId(), command.getVehicleId(),
                command.getIssuedByUserId(), command.getType().name(),
                command.getTriggeredByAlertId(), command.getStatus().name(),
                command.getIssuedAt(), command.getDispatchedAt(),
                command.getAcknowledgedAt(), command.getCompletedAt(),
                command.getResult() != null ? command.getResult().name() : null);
    }
}
