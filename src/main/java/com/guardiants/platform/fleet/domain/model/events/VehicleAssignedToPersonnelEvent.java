package com.guardiants.platform.fleet.domain.model.events;

public record VehicleAssignedToPersonnelEvent(Long loanId, Long vehicleId, Long personnelId) {}
