package com.guardiants.platform.commands.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.commands.domain.model.aggregates.Command;
import com.guardiants.platform.commands.domain.model.valueobjects.CommandResult;
import com.guardiants.platform.commands.domain.model.valueobjects.CommandStatus;
import com.guardiants.platform.commands.domain.model.valueobjects.CommandType;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.entities.CommandPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class CommandEntityAssembler {

    public CommandPersistenceEntity toPersistenceEntityFromDomain(Command command) {
        var entity = new CommandPersistenceEntity();
        entity.setId(command.getId());
        entity.setVehicleId(command.getVehicleId());
        entity.setIssuedByUserId(command.getIssuedByUserId());
        entity.setType(command.getType().name());
        entity.setTriggeredByAlertId(command.getTriggeredByAlertId());
        entity.setStatus(command.getStatus().name());
        entity.setIssuedAt(command.getIssuedAt());
        entity.setDispatchedAt(command.getDispatchedAt());
        entity.setAcknowledgedAt(command.getAcknowledgedAt());
        entity.setCompletedAt(command.getCompletedAt());
        entity.setResult(command.getResult() != null ? command.getResult().name() : null);
        return entity;
    }

    public Command toDomainFromPersistenceEntity(CommandPersistenceEntity entity) {
        var command = new Command(
                entity.getVehicleId(),
                entity.getIssuedByUserId(),
                CommandType.valueOf(entity.getType()),
                entity.getTriggeredByAlertId(),
                CommandStatus.valueOf(entity.getStatus()),
                entity.getIssuedAt(),
                entity.getDispatchedAt(),
                entity.getAcknowledgedAt(),
                entity.getCompletedAt(),
                entity.getResult() != null ? CommandResult.valueOf(entity.getResult()) : null);
        command.setId(entity.getId());
        return command;
    }
}
