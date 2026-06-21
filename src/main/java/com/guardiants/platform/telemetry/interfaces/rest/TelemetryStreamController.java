package com.guardiants.platform.telemetry.interfaces.rest;

import com.guardiants.platform.telemetry.application.queryservices.TelemetryQueryService;
import com.guardiants.platform.telemetry.domain.model.queries.StreamLiveTelemetryQuery;
import com.guardiants.platform.telemetry.interfaces.rest.transform.TelemetryPointResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/telemetry", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
@Tag(name = "Telemetry Stream", description = "SSE endpoints for live telemetry streaming")
public class TelemetryStreamController {

    private final TelemetryQueryService telemetryQueryService;

    public TelemetryStreamController(TelemetryQueryService telemetryQueryService) {
        this.telemetryQueryService = telemetryQueryService;
    }

    @Operation(summary = "Subscribe to live telemetry (SSE)",
            description = "Streams real-time telemetry points for a vehicle as Server-Sent Events.")
    @GetMapping("/vehicles/{vehicleId}/stream")
    public SseEmitter streamLiveTelemetry(@PathVariable Long vehicleId) {
        log.debug("SSE /api/v1/telemetry/vehicles/{}/stream", vehicleId);
        var emitter = new SseEmitter(0L); // no timeout
        var flux = telemetryQueryService.handle(new StreamLiveTelemetryQuery(vehicleId));
        var subscription = flux.subscribe(
                point -> {
                    try {
                        emitter.send(SseEmitter.event()
                                .data(TelemetryPointResourceFromEntityAssembler
                                        .toResourceFromEntity(point)));
                    } catch (Exception e) {
                        emitter.completeWithError(e);
                    }
                },
                emitter::completeWithError,
                emitter::complete);
        emitter.onCompletion(subscription::dispose);
        emitter.onTimeout(subscription::dispose);
        return emitter;
    }
}
