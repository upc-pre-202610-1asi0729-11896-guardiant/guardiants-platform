package com.guardiants.platform.alerting.interfaces.rest.resources;

import java.time.Instant;

public record SecurityAlertResource(
        Long id,
        Long ownerId,
        Long vehicleId,
        Long ruleId,
        String type,
        String severity,
        GeoPointResource location,
        String description,
        Instant generatedAt,
        String status,
        Instant acknowledgedAt,
        Instant closedAt) {
}
