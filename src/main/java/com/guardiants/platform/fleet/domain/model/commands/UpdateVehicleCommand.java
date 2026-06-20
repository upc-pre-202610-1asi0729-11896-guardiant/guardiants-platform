package com.guardiants.platform.fleet.domain.model.commands;

public record UpdateVehicleCommand(Long vehicleId, String model, String brand, int year) {}
