package com.guardiants.platform.telemetry.domain.model.events;

import java.time.Instant;

public record DeviceConnectionRestoredEvent(Long vehicleId, Instant occurredAt) {
}
