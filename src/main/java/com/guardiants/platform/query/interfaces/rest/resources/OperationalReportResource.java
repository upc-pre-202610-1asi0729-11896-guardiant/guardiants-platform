package com.guardiants.platform.query.interfaces.rest.resources;

import java.time.Instant;
import java.util.List;

public record OperationalReportResource(
        Long id, Long fleetId, Instant periodStart, Instant periodEnd,
        List<VehicleOperationalSummaryResource> vehicleSummaries,
        int totalAlertsCount, int totalLoansCount, Instant generatedAt) {}
