package com.guardiants.platform.commands.domain.model.commands;

import com.guardiants.platform.commands.domain.model.valueobjects.CommandResult;
import com.guardiants.platform.commands.domain.model.valueobjects.CommandStatus;

public record UpdateCommandStatusCommand(
        Long commandId,
        CommandStatus status,
        CommandResult result) {}
