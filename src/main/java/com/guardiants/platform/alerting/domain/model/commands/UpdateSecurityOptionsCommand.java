package com.guardiants.platform.alerting.domain.model.commands;

public record UpdateSecurityOptionsCommand(
        Long ownerId,
        Boolean suspiciousMovementEnabled,
        Boolean autoEngineShutdownEnabled,
        Boolean autoSafeModeEnabled) {
}
