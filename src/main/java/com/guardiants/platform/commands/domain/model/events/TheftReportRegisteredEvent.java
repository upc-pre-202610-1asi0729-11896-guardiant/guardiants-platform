package com.guardiants.platform.commands.domain.model.events;

public record TheftReportRegisteredEvent(Long reportId, Long vehicleId,
        Long reportedByUserId) {}
