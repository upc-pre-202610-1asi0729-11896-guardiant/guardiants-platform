package com.guardiants.platform.telemetry.interfaces.rest;

import com.guardiants.platform.telemetry.application.commandservices.TelemetryCommandService;
import com.guardiants.platform.telemetry.application.queryservices.TelemetryQueryService;
import com.guardiants.platform.telemetry.domain.model.queries.GetLatestTelemetryPointQuery;
import com.guardiants.platform.telemetry.domain.model.queries.GetVehicleGeneralStatusQuery;
import com.guardiants.platform.telemetry.domain.repositories.TelemetryPointRepository;
import com.guardiants.platform.telemetry.interfaces.rest.resources.IngestTelemetryPointResource;
import com.guardiants.platform.telemetry.interfaces.rest.transform.IngestTelemetryPointCommandFromResourceAssembler;
import com.guardiants.platform.telemetry.interfaces.rest.transform.ResponseEntityFromTelemetryCommandResultAssembler;
import com.guardiants.platform.telemetry.interfaces.rest.transform.ResponseEntityFromTelemetryQueryResultAssembler;
import com.guardiants.platform.telemetry.interfaces.rest.transform.TelemetryPointResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/telemetry", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Telemetry", description = "Endpoints for IoT telemetry ingestion and vehicle status")
public class TelemetryController {

    private final TelemetryCommandService telemetryCommandService;
    private final TelemetryQueryService telemetryQueryService;
    private final TelemetryPointRepository telemetryPointRepository;
    private final MessageSource messageSource;

    public TelemetryController(TelemetryCommandService telemetryCommandService,
                               TelemetryQueryService telemetryQueryService,
                               TelemetryPointRepository telemetryPointRepository,
                               MessageSource messageSource) {
        this.telemetryCommandService = telemetryCommandService;
        this.telemetryQueryService = telemetryQueryService;
        this.telemetryPointRepository = telemetryPointRepository;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Ingest telemetry point (TS38)",
            description = "Registers a GPS/IoT telemetry data point for a vehicle. Called by MQTT adapter or directly via REST.")
    @PostMapping
    public ResponseEntity<?> ingestTelemetryPoint(
            @Valid @RequestBody IngestTelemetryPointResource resource) {
        log.debug("POST /api/v1/telemetry - vehicleId={}, deviceSerial={}",
                resource.vehicleId(), resource.deviceSerial());
        var command = IngestTelemetryPointCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var result = telemetryCommandService.handle(command);
        return ResponseEntityFromTelemetryCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get vehicle general status (TS39)",
            description = "Returns real-time status of a vehicle: location, battery, GPS signal and lock state.")
    @GetMapping("/vehicles/{vehicleId}/status")
    public ResponseEntity<?> getVehicleGeneralStatus(@PathVariable Long vehicleId) {
        log.debug("GET /api/v1/telemetry/vehicles/{}/status", vehicleId);
        var status = telemetryQueryService.handle(new GetVehicleGeneralStatusQuery(vehicleId));
        if (status.isEmpty()) {
            return ResponseEntityFromTelemetryQueryResultAssembler.notFound(
                    messageSource, "telemetry.error.vehicleNotFound", vehicleId);
        }
        var latestPoint = telemetryPointRepository.findLatestByVehicleId(vehicleId)
                .map(TelemetryPointResourceFromEntityAssembler::toResourceFromEntity)
                .orElse(null);
        return ResponseEntityFromTelemetryQueryResultAssembler
                .toResponseEntityFromVehicleGeneralStatus(status, latestPoint);
    }

    @Operation(summary = "Get vehicle location (TS39)",
            description = "Returns lat, lng, speed, timestamp and signal quality for a vehicle.")
    @GetMapping("/vehicles/{vehicleId}/location")
    public ResponseEntity<?> getVehicleLocation(@PathVariable Long vehicleId) {
        log.debug("GET /api/v1/telemetry/vehicles/{}/location", vehicleId);
        var point = telemetryQueryService.handle(new GetLatestTelemetryPointQuery(vehicleId));
        if (point.isEmpty()) {
            return ResponseEntityFromTelemetryQueryResultAssembler.notFound(
                    messageSource, "telemetry.error.vehicleNotFound", vehicleId);
        }
        return ResponseEntity.ok(
                TelemetryPointResourceFromEntityAssembler.toResourceFromEntity(point.get()));
    }
}
