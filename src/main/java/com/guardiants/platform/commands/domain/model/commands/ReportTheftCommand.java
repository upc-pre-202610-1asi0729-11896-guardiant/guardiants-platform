package com.guardiants.platform.commands.domain.model.commands;

public record ReportTheftCommand(Long vehicleId, Long reportedByUserId) {}
