package com.guardiants.platform.query.domain.model.queries;

import java.time.Instant;

public record GetRouteHistoryQuery(Long vehicleId, Instant startDate, Instant endDate) {
}
