package com.guardiants.platform.fleet.domain.model.commands;

public record RegisterVehicleCommand(
        Long fleetId, String plate, String model, String brand, int year) {}
