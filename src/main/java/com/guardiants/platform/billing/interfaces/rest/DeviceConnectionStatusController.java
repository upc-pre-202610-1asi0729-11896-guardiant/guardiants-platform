package com.guardiants.platform.billing.interfaces.rest;

import com.guardiants.platform.billing.application.queryservices.DeviceConnectionStatusQueryService;
import com.guardiants.platform.billing.domain.model.queries.GetDeviceConnectionStatusQuery;
import com.guardiants.platform.billing.interfaces.rest.transform.DeviceConnectionStatusResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/billing/device-connection-status",
        produces = APPLICATION_JSON_VALUE)
@Tag(name = "Device Connection Status")
public class DeviceConnectionStatusController {

    private final DeviceConnectionStatusQueryService queryService;

    public DeviceConnectionStatusController(DeviceConnectionStatusQueryService queryService) {
        this.queryService = queryService;
    }

    @Operation(summary = "Get device connection status")
    @GetMapping
    public ResponseEntity<?> getDeviceConnectionStatus(@RequestParam Long ownerId) {
        return queryService.handle(new GetDeviceConnectionStatusQuery(ownerId))
                .map(s -> ResponseEntity.ok(
                        DeviceConnectionStatusResourceFromEntityAssembler
                                .toResourceFromEntity(s)))
                .orElse(ResponseEntity.notFound().build());
    }
}
