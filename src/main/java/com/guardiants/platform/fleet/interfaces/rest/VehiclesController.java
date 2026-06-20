package com.guardiants.platform.fleet.interfaces.rest;

import com.guardiants.platform.fleet.application.commandservices.VehicleCommandService;
import com.guardiants.platform.fleet.interfaces.rest.resources.RegisterVehicleResource;
import com.guardiants.platform.fleet.interfaces.rest.transform.RegisterVehicleCommandFromResourceAssembler;
import com.guardiants.platform.fleet.interfaces.rest.transform.ResponseEntityFromVehicleCommandResultAssembler;
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
@RequestMapping(value = "/api/v1/fleet/vehicles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Vehicles", description = "Vehicle registration and management")
public class VehiclesController {

    private final VehicleCommandService vehicleCommandService;
    private final MessageSource messageSource;

    public VehiclesController(VehicleCommandService vehicleCommandService,
                              MessageSource messageSource) {
        this.vehicleCommandService = vehicleCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Register vehicle")
    @PostMapping
    public ResponseEntity<?> registerVehicle(
            @Valid @RequestBody RegisterVehicleResource resource) {
        log.debug("POST /api/v1/fleet/vehicles - plate={}", resource.plate());
        var command = RegisterVehicleCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = vehicleCommandService.handle(command);
        return ResponseEntityFromVehicleCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}
