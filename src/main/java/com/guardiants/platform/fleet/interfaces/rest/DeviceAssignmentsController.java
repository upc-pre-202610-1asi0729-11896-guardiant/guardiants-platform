package com.guardiants.platform.fleet.interfaces.rest;

import com.guardiants.platform.fleet.application.commandservices.VehicleCommandService;
import com.guardiants.platform.fleet.domain.model.commands.AssignDeviceCommand;
import com.guardiants.platform.fleet.domain.model.commands.UnassignDeviceCommand;
import com.guardiants.platform.fleet.interfaces.rest.resources.AssignDeviceResource;
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
@RequestMapping(value = "/api/v1/fleet/vehicles/{vehicleId}/device-assignments",
        produces = APPLICATION_JSON_VALUE)
@Tag(name = "Device Assignments", description = "IoT device assignment to vehicles")
public class DeviceAssignmentsController {

    private final VehicleCommandService vehicleCommandService;
    private final MessageSource messageSource;

    public DeviceAssignmentsController(VehicleCommandService vehicleCommandService,
                                       MessageSource messageSource) {
        this.vehicleCommandService = vehicleCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Assign IoT device to vehicle")
    @PostMapping
    public ResponseEntity<?> assignDevice(
            @PathVariable Long vehicleId,
            @Valid @RequestBody AssignDeviceResource resource) {
        log.debug("POST /api/v1/fleet/vehicles/{}/device-assignments", vehicleId);
        var result = vehicleCommandService.handle(
                new AssignDeviceCommand(vehicleId, resource.deviceSerial()));
        return ResponseEntityFromVehicleCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Unassign IoT device from vehicle")
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<?> unassignDevice(@PathVariable Long vehicleId,
                                            @PathVariable Long assignmentId) {
        var result = vehicleCommandService.handle(new UnassignDeviceCommand(assignmentId));
        return ResponseEntityFromVehicleCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}
