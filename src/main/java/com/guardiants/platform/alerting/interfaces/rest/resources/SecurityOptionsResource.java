package com.guardiants.platform.alerting.interfaces.rest.resources;

import java.time.Instant;

public record SecurityOptionsResource(
        Long id,
        Long ownerId,
        boolean suspiciousMovementEnabled,
        boolean autoEngineShutdownEnabled,
        boolean autoSafeModeEnabled,
        Instant updatedAt) {
}
