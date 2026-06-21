package com.guardiants.platform.commands.domain.model.events;

public record DeviceRestartedEvent(Long vehicleId, Long commandId) {}
