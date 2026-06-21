package com.guardiants.platform.telemetry.domain.model.queries;

import java.time.Instant;

public record GetRouteHistoryQuery(Long vehicleId, Instant startDate, Instant endDate) {
}
