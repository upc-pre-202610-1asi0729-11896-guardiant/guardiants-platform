package com.guardiants.platform.alerting.interfaces.rest.resources;

public record UpdateSecurityOptionsResource(
        Boolean suspiciousMovementEnabled,
        Boolean autoEngineShutdownEnabled,
        Boolean autoSafeModeEnabled) {
}
