package com.guardiants.platform.query.interfaces.rest.resources;

import java.time.Instant;
import java.util.List;

public record RouteHistoryResource(
        Long id,
        Long vehicleId,
        Instant periodStart,
        Instant periodEnd,
        List<RouteSegmentResource> segments,
        double totalDistanceKm,
        int totalDurationMinutes,
        int totalTripsCount) {
}
