package com.guardiants.platform.commands.application.internal.eventhandlers;

import com.guardiants.platform.commands.application.commandservices.CommandCommandService;
import com.guardiants.platform.commands.domain.model.commands.UpdateCommandStatusCommand;
import com.guardiants.platform.commands.domain.model.events.CommandAckReceivedEvent;
import com.guardiants.platform.commands.domain.model.valueobjects.CommandResult;
import com.guardiants.platform.commands.domain.model.valueobjects.CommandStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listens to {@link CommandAckReceivedEvent} published by the EMQX dispatch adapter on
 * device ACK, and transitions the command to its terminal status.
 */
@Slf4j
@Component
public class CommandAckEventHandler {

    private final CommandCommandService commandCommandService;

    public CommandAckEventHandler(CommandCommandService commandCommandService) {
        this.commandCommandService = commandCommandService;
    }

    @EventListener
    public void on(CommandAckReceivedEvent event) {
        log.debug("CommandAckEventHandler: commandId={} success={}",
                event.commandId(), event.success());
        commandCommandService.handle(new UpdateCommandStatusCommand(
                event.commandId(),
                event.success() ? CommandStatus.COMPLETED : CommandStatus.FAILED,
                event.success() ? CommandResult.SUCCEEDED : CommandResult.FAILED));
    }
}
