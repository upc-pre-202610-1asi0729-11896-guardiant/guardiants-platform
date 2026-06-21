package com.guardiants.platform.commands.application.commandservices;

import com.guardiants.platform.commands.domain.model.aggregates.Command;
import com.guardiants.platform.commands.domain.model.commands.IssueEngineBlockCommand;
import com.guardiants.platform.commands.domain.model.commands.IssueEngineUnblockCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface CommandCommandService {
    Result<Command, CommandFailure> handle(IssueEngineBlockCommand command);
    Result<Command, CommandFailure> handle(IssueEngineUnblockCommand command);
}
