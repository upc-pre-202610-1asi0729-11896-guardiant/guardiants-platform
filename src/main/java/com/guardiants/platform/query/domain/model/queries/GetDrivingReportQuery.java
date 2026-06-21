package com.guardiants.platform.query.domain.model.queries;

import java.time.Instant;

public record GetDrivingReportQuery(Long vehicleId, Instant startDate, Instant endDate) {
}
