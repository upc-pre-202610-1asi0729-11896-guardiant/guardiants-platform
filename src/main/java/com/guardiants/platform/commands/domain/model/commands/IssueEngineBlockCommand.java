package com.guardiants.platform.commands.domain.model.commands;

public record IssueEngineBlockCommand(
        Long vehicleId,
        Long issuedByUserId,
        Long triggeredByAlertId) {
}
