package com.guardiants.platform.query.domain.model.queries;

import java.time.Instant;

public record GetOperationalReportQuery(Long fleetId, Instant startDate, Instant endDate) {
}
