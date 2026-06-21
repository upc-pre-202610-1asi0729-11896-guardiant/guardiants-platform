package com.guardiants.platform.commands.interfaces.rest;

import com.guardiants.platform.commands.application.commandservices.CommandCommandService;
import com.guardiants.platform.commands.domain.model.commands.IssueDeviceRestartCommand;
import com.guardiants.platform.commands.domain.model.commands.IssueEngineUnblockCommand;
import com.guardiants.platform.commands.interfaces.rest.resources.IssueDeviceRestartResource;
import com.guardiants.platform.commands.interfaces.rest.resources.IssueEngineBlockResource;
import com.guardiants.platform.commands.interfaces.rest.resources.IssueEngineUnblockResource;
import com.guardiants.platform.commands.interfaces.rest.transform.IssueEngineBlockCommandFromResourceAssembler;
import com.guardiants.platform.commands.interfaces.rest.transform.ResponseEntityFromCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/commands", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Commands", description = "Remote IoT device command dispatching")
public class CommandsController {

    private final CommandCommandService commandCommandService;
    private final MessageSource messageSource;

    public CommandsController(CommandCommandService commandCommandService,
                             MessageSource messageSource) {
        this.commandCommandService = commandCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Issue engine block command",
            description = "Sends an engine block command to the IoT device via MQTT.")
    @PostMapping("/engine-block")
    public ResponseEntity<?> issueEngineBlock(
            @Valid @RequestBody IssueEngineBlockResource resource) {
        log.debug("POST /api/v1/commands/engine-block - vehicleId={}",
                resource.vehicleId());
        var command = IssueEngineBlockCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var result = commandCommandService.handle(command);
        return ResponseEntityFromCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Issue engine unblock command")
    @PostMapping("/engine-unblock")
    public ResponseEntity<?> issueEngineUnblock(
            @Valid @RequestBody IssueEngineUnblockResource resource) {
        var result = commandCommandService.handle(
                new IssueEngineUnblockCommand(resource.vehicleId(), resource.issuedByUserId()));
        return ResponseEntityFromCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Issue device restart command")
    @PostMapping("/device-restart")
    public ResponseEntity<?> issueDeviceRestart(
            @Valid @RequestBody IssueDeviceRestartResource resource) {
        var result = commandCommandService.handle(
                new IssueDeviceRestartCommand(resource.vehicleId(), resource.issuedByUserId()));
        return ResponseEntityFromCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}
