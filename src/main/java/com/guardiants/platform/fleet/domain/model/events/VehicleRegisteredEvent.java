package com.guardiants.platform.fleet.domain.model.events;

public record VehicleRegisteredEvent(Long vehicleId, Long fleetId, String plate) {}
