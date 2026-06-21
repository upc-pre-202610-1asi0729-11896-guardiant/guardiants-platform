package com.guardiants.platform.telemetry.infrastructure.mqtt.emqx;

import com.guardiants.platform.telemetry.application.commandservices.TelemetryCommandService;
import com.guardiants.platform.telemetry.application.internal.outboundservices.mqtt.TelemetryIngestionPort;
import com.guardiants.platform.telemetry.domain.model.commands.IngestTelemetryPointCommand;
import com.guardiants.platform.telemetry.domain.model.valueobjects.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Slf4j
@Component
public class EmqxTelemetryIngestionAdapter implements TelemetryIngestionPort {

    private final TelemetryCommandService telemetryCommandService;
    private final ObjectMapper objectMapper;

    public EmqxTelemetryIngestionAdapter(TelemetryCommandService telemetryCommandService,
                                         ObjectMapper objectMapper) {
        this.telemetryCommandService = telemetryCommandService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(String topic, byte[] payload) {
        try {
            var dto = objectMapper.readValue(payload, TelemetryPayloadDto.class);
            var command = new IngestTelemetryPointCommand(
                    dto.deviceSerial(),
                    dto.vehicleId(),
                    Instant.parse(dto.timestamp()),
                    new GeoPoint(dto.lat(), dto.lng()),
                    new VehicleStatusSnapshot(dto.speedKmh(), dto.fuelLevelPercent(),
                            dto.engineTemperatureC(), dto.batteryLevelPercent(),
                            dto.odometerKm(), dto.rpm(), dto.engineOn()),
                    ConnectivityValue.valueOf(dto.connectivity()));
            telemetryCommandService.handle(command).fold(point -> point, failure -> {
                log.warn("Telemetry ingestion failed for topic={}: {}", topic, failure.messageKey());
                return null;
            });
        } catch (Exception e) {
            log.error("Failed to parse MQTT payload on topic={}", topic, e);
        }
    }
}
