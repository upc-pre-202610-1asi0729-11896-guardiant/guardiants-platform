package com.guardiants.platform.commands.interfaces.rest;

import com.guardiants.platform.commands.application.queryservices.DeviceHealthQueryService;
import com.guardiants.platform.commands.domain.model.queries.GetDeviceHealthQuery;
import com.guardiants.platform.commands.interfaces.rest.transform.DeviceHealthResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/commands/device-health", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Device Health", description = "IoT device health and connectivity status")
public class DeviceHealthController {

    private final DeviceHealthQueryService deviceHealthQueryService;

    public DeviceHealthController(DeviceHealthQueryService deviceHealthQueryService) {
        this.deviceHealthQueryService = deviceHealthQueryService;
    }

    @Operation(summary = "Get device health",
            description = "Returns battery level, connectivity status and last heartbeat for a vehicle's IoT device.")
    @GetMapping
    public ResponseEntity<?> getDeviceHealth(@RequestParam Long vehicleId) {
        log.debug("GET /api/v1/commands/device-health?vehicleId={}", vehicleId);
        return deviceHealthQueryService.handle(new GetDeviceHealthQuery(vehicleId))
                .map(h -> ResponseEntity.ok(
                        DeviceHealthResourceFromEntityAssembler.toResourceFromEntity(h)))
                .orElse(ResponseEntity.notFound().build());
    }
}
