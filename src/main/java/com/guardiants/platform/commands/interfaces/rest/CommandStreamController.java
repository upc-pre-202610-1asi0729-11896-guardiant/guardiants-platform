package com.guardiants.platform.commands.interfaces.rest;

import com.guardiants.platform.commands.application.queryservices.CommandQueryService;
import com.guardiants.platform.commands.domain.model.queries.StreamCommandStatusQuery;
import com.guardiants.platform.commands.interfaces.rest.transform.CommandResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/commands", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
@Tag(name = "Command Stream", description = "SSE endpoint for command status updates")
public class CommandStreamController {

    private final CommandQueryService commandQueryService;

    public CommandStreamController(CommandQueryService commandQueryService) {
        this.commandQueryService = commandQueryService;
    }

    @Operation(summary = "Subscribe to command status updates (SSE)",
            description = "Streams real-time command status changes for a specific command.")
    @GetMapping("/{commandId}/stream")
    public SseEmitter subscribeToCommandStatus(@PathVariable Long commandId) {
        log.debug("SSE /api/v1/commands/{}/stream", commandId);
        var emitter = new SseEmitter(0L);
        var flux = commandQueryService.handle(new StreamCommandStatusQuery(commandId));
        var subscription = flux.subscribe(
                command -> {
                    try {
                        emitter.send(SseEmitter.event()
                                .data(CommandResourceFromEntityAssembler
                                        .toResourceFromEntity(command)));
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                    }
                },
                emitter::completeWithError,
                emitter::complete);
        emitter.onCompletion(subscription::dispose);
        emitter.onTimeout(subscription::dispose);
        return emitter;
    }
}
