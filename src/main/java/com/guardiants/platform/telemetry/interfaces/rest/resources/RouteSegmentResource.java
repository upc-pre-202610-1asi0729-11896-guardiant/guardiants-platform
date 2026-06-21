package com.guardiants.platform.telemetry.interfaces.rest.resources;

import java.time.Instant;
import java.util.List;

public record RouteSegmentResource(
        Long id,
        Long vehicleId,
        Instant startedAt,
        Instant endedAt,
        double distanceKm,
        List<GeoPointResource> points) {
}
