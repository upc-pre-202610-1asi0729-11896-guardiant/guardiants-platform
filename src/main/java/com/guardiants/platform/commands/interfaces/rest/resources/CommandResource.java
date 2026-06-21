package com.guardiants.platform.commands.interfaces.rest.resources;

import java.time.Instant;

public record CommandResource(Long id, Long vehicleId, Long issuedByUserId,
        String type, Long triggeredByAlertId, String status,
        Instant issuedAt, Instant dispatchedAt, Instant acknowledgedAt,
        Instant completedAt, String result) {}
