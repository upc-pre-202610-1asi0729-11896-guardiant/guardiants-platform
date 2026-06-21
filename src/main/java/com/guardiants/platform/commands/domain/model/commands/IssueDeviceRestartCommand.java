package com.guardiants.platform.commands.domain.model.commands;

public record IssueDeviceRestartCommand(Long vehicleId, Long issuedByUserId) {}
