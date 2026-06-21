package com.guardiants.platform.telemetry.domain.model.commands;

public record SetEngineLockStatusCommand(Long vehicleId, boolean locked) {
}
