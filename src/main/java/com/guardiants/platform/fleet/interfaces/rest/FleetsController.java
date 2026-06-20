package com.guardiants.platform.fleet.interfaces.rest;

import com.guardiants.platform.fleet.application.commandservices.FleetCommandService;
import com.guardiants.platform.fleet.interfaces.rest.resources.CreateFleetResource;
import com.guardiants.platform.fleet.interfaces.rest.transform.CreateFleetCommandFromResourceAssembler;
import com.guardiants.platform.fleet.interfaces.rest.transform.ResponseEntityFromFleetCommandResultAssembler;
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
@RequestMapping(value = "/api/v1/fleet/fleets", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fleets", description = "Fleet management")
public class FleetsController {

    private final FleetCommandService fleetCommandService;
    private final MessageSource messageSource;

    public FleetsController(FleetCommandService fleetCommandService,
                            MessageSource messageSource) {
        this.fleetCommandService = fleetCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create fleet",
            description = "Creates a new fleet for an owner.")
    @PostMapping
    public ResponseEntity<?> createFleet(
            @Valid @RequestBody CreateFleetResource resource) {
        log.debug("POST /api/v1/fleet/fleets - ownerId={}", resource.ownerId());
        var command = CreateFleetCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = fleetCommandService.handle(command);
        return ResponseEntityFromFleetCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}
