package com.guardiants.platform.fleet.domain.model.commands;

public record AssignDeviceCommand(Long vehicleId, String deviceSerial) {}
