package com.guardiants.platform.fleet.domain.model.commands;

public record RejectVehicleLoanCommand(Long loanId, Long approverId, String reason) {}
