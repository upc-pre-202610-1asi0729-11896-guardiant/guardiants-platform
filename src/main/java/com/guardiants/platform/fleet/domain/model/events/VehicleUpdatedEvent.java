package com.guardiants.platform.fleet.domain.model.events;

public record VehicleUpdatedEvent(Long vehicleId, String plate) {}
