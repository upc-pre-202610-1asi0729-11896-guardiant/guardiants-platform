package com.guardiants.platform.telemetry.interfaces.rest;

import com.guardiants.platform.telemetry.application.commandservices.TelemetryCommandService;
import com.guardiants.platform.telemetry.interfaces.rest.resources.IngestTelemetryPointResource;
import com.guardiants.platform.telemetry.interfaces.rest.transform.IngestTelemetryPointCommandFromResourceAssembler;
import com.guardiants.platform.telemetry.interfaces.rest.transform.ResponseEntityFromTelemetryCommandResultAssembler;
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
    private final MessageSource messageSource;

    public TelemetryController(TelemetryCommandService telemetryCommandService,
                               MessageSource messageSource) {
        this.telemetryCommandService = telemetryCommandService;
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
}
