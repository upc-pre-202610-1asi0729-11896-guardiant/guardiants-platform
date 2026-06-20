package com.guardiants.platform.fleet.interfaces.rest;

import com.guardiants.platform.fleet.application.commandservices.FleetCommandService;
import com.guardiants.platform.fleet.application.queryservices.FleetQueryService;
import com.guardiants.platform.fleet.domain.model.queries.GetAllFleetsByOwnerIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetFleetByIdQuery;
import com.guardiants.platform.fleet.interfaces.rest.resources.CreateFleetResource;
import com.guardiants.platform.fleet.interfaces.rest.resources.FleetResource;
import com.guardiants.platform.fleet.interfaces.rest.transform.CreateFleetCommandFromResourceAssembler;
import com.guardiants.platform.fleet.interfaces.rest.transform.FleetResourceFromEntityAssembler;
import com.guardiants.platform.fleet.interfaces.rest.transform.ResponseEntityFromFleetCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/fleet/fleets", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fleets", description = "Fleet management")
public class FleetsController {

    private final FleetCommandService fleetCommandService;
    private final FleetQueryService fleetQueryService;
    private final MessageSource messageSource;

    public FleetsController(FleetCommandService fleetCommandService,
                            FleetQueryService fleetQueryService,
                            MessageSource messageSource) {
        this.fleetCommandService = fleetCommandService;
        this.fleetQueryService = fleetQueryService;
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

    @Operation(summary = "Get fleet by ID")
    @GetMapping("/{fleetId}")
    public ResponseEntity<?> getFleetById(@PathVariable Long fleetId) {
        return fleetQueryService.handle(new GetFleetByIdQuery(fleetId))
                .map(f -> ResponseEntity.ok(FleetResourceFromEntityAssembler.toResourceFromEntity(f)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all fleets by owner")
    @GetMapping
    public ResponseEntity<List<FleetResource>> getAllFleetsByOwnerId(@RequestParam Long ownerId) {
        return ResponseEntity.ok(fleetQueryService.handle(new GetAllFleetsByOwnerIdQuery(ownerId))
                .stream().map(FleetResourceFromEntityAssembler::toResourceFromEntity).toList());
    }
}
