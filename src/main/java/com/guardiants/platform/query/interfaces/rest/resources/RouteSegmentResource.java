package com.guardiants.platform.query.interfaces.rest.resources;

import java.time.Instant;

public record RouteSegmentResource(
        Long segmentId,
        Instant startedAt,
        Instant endedAt,
        double startLat,
        double startLng,
        double endLat,
        double endLng,
        double distanceKm,
        int durationMinutes) {
}
