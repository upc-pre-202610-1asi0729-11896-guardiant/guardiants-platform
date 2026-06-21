package com.guardiants.platform.alerting.interfaces.rest.resources;

public record GenerateSecurityAlertResource(
        Long ownerId,
        Long vehicleId,
        Long ruleId,
        String type,
        String severity,
        double lat,
        double lng,
        String description) {
}
