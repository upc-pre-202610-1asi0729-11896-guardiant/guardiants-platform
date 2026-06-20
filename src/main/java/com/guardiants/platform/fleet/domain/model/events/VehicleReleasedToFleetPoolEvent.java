package com.guardiants.platform.fleet.domain.model.events;

public record VehicleReleasedToFleetPoolEvent(Long loanId, Long vehicleId) {}
