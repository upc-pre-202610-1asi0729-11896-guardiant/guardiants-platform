package com.guardiants.platform.query.interfaces.rest.resources;

import java.time.Instant;

public record SearchResultResource(
        String entityType,
        Long entityId,
        String label,
        String subtitle,
        Double lat,
        Double lng,
        Instant occurredAt) {
}
