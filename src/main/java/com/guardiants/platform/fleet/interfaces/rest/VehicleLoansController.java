package com.guardiants.platform.fleet.interfaces.rest;

import com.guardiants.platform.fleet.application.commandservices.VehicleLoanCommandService;
import com.guardiants.platform.fleet.interfaces.rest.resources.RequestVehicleLoanResource;
import com.guardiants.platform.fleet.interfaces.rest.transform.RequestVehicleLoanCommandFromResourceAssembler;
import com.guardiants.platform.fleet.interfaces.rest.transform.ResponseEntityFromVehicleLoanCommandResultAssembler;
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
@RequestMapping(value = "/api/v1/fleet/vehicle-loans", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Vehicle Loans", description = "Vehicle loan workflow management")
public class VehicleLoansController {

    private final VehicleLoanCommandService vehicleLoanCommandService;
    private final MessageSource messageSource;

    public VehicleLoansController(VehicleLoanCommandService vehicleLoanCommandService,
                                  MessageSource messageSource) {
        this.vehicleLoanCommandService = vehicleLoanCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Request vehicle loan",
            description = "Personnel requests a vehicle from the fleet pool.")
    @PostMapping
    public ResponseEntity<?> requestVehicleLoan(
            @Valid @RequestBody RequestVehicleLoanResource resource) {
        log.debug("POST /api/v1/fleet/vehicle-loans - vehicleId={}", resource.vehicleId());
        var command = RequestVehicleLoanCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = vehicleLoanCommandService.handle(command);
        return ResponseEntityFromVehicleLoanCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}
