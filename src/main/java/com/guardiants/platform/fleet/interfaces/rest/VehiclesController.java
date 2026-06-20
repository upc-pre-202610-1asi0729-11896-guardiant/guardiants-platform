package com.guardiants.platform.fleet.interfaces.rest;

import com.guardiants.platform.fleet.application.commandservices.VehicleCommandService;
import com.guardiants.platform.fleet.application.queryservices.VehicleQueryService;
import com.guardiants.platform.fleet.domain.model.commands.DeactivateVehicleCommand;
import com.guardiants.platform.fleet.domain.model.commands.UpdateVehicleCommand;
import com.guardiants.platform.fleet.domain.model.queries.GetAllVehiclesByFleetIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetVehicleByIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetVehicleByPlateQuery;
import com.guardiants.platform.fleet.domain.repositories.DeviceAssignmentRepository;
import com.guardiants.platform.fleet.interfaces.rest.resources.RegisterVehicleResource;
import com.guardiants.platform.fleet.interfaces.rest.resources.UpdateVehicleResource;
import com.guardiants.platform.fleet.interfaces.rest.resources.VehicleResource;
import com.guardiants.platform.fleet.interfaces.rest.transform.RegisterVehicleCommandFromResourceAssembler;
import com.guardiants.platform.fleet.interfaces.rest.transform.ResponseEntityFromVehicleCommandResultAssembler;
import com.guardiants.platform.fleet.interfaces.rest.transform.VehicleResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/fleet/vehicles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Vehicles", description = "Vehicle registration and management")
public class VehiclesController {

    private final VehicleCommandService vehicleCommandService;
    private final VehicleQueryService vehicleQueryService;
    private final DeviceAssignmentRepository deviceAssignmentRepository;
    private final MessageSource messageSource;

    public VehiclesController(VehicleCommandService vehicleCommandService,
                              VehicleQueryService vehicleQueryService,
                              DeviceAssignmentRepository deviceAssignmentRepository,
                              MessageSource messageSource) {
        this.vehicleCommandService = vehicleCommandService;
        this.vehicleQueryService = vehicleQueryService;
        this.deviceAssignmentRepository = deviceAssignmentRepository;
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

    @Operation(summary = "Update vehicle")
    @PutMapping("/{vehicleId}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long vehicleId,
                                           @Valid @RequestBody UpdateVehicleResource resource) {
        var result = vehicleCommandService.handle(
                new UpdateVehicleCommand(vehicleId, resource.model(),
                        resource.brand(), resource.year()));
        return ResponseEntityFromVehicleCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Deactivate vehicle")
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<?> deactivateVehicle(@PathVariable Long vehicleId) {
        var result = vehicleCommandService.handle(new DeactivateVehicleCommand(vehicleId));
        return ResponseEntityFromVehicleCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get vehicle by ID")
    @GetMapping("/{vehicleId}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long vehicleId) {
        return vehicleQueryService.handle(new GetVehicleByIdQuery(vehicleId))
                .map(v -> {
                    var assignment = deviceAssignmentRepository.findActiveByVehicleId(v.getId());
                    return ResponseEntity.ok(
                            VehicleResourceFromEntityAssembler.toResourceFromEntity(v, assignment));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all vehicles by fleet")
    @GetMapping
    public ResponseEntity<List<VehicleResource>> getAllVehiclesByFleetId(@RequestParam Long fleetId) {
        return ResponseEntity.ok(vehicleQueryService.handle(new GetAllVehiclesByFleetIdQuery(fleetId))
                .stream().map(v -> {
                    var a = deviceAssignmentRepository.findActiveByVehicleId(v.getId());
                    return VehicleResourceFromEntityAssembler.toResourceFromEntity(v, a);
                }).toList());
    }

    @Operation(summary = "Get vehicle by plate")
    @GetMapping("/by-plate")
    public ResponseEntity<?> getVehicleByPlate(@RequestParam String plate) {
        return vehicleQueryService.handle(new GetVehicleByPlateQuery(plate))
                .map(v -> {
                    var a = deviceAssignmentRepository.findActiveByVehicleId(v.getId());
                    return ResponseEntity.ok(
                            VehicleResourceFromEntityAssembler.toResourceFromEntity(v, a));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
