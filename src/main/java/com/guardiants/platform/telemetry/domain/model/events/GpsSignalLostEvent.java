package com.guardiants.platform.telemetry.domain.model.events;

import java.time.Instant;

public record GpsSignalLostEvent(Long vehicleId, Instant occurredAt) {
}
