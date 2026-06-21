package com.guardiants.platform.commands.interfaces.rest.resources;

import java.time.Instant;
import java.util.List;

public record TheftReportResource(Long id, Long vehicleId, Long reportedByUserId,
        Instant reportedAt, String status,
        List<Long> relatedCommandIds, Long relatedAlertId) {}
