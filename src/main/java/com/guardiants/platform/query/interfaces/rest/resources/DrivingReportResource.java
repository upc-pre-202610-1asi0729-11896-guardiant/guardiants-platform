package com.guardiants.platform.query.interfaces.rest.resources;

import java.time.Instant;
import java.util.List;

public record DrivingReportResource(
        Long id, Long vehicleId, Instant periodStart, Instant periodEnd,
        double totalDistanceKm, int totalDurationMinutes, double averageSpeedKmh,
        double drivingScore, int harshBrakingEvents, int harshAccelerationEvents,
        List<RiskPatternResource> riskPatterns) {}
