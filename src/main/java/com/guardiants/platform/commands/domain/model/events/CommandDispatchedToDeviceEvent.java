package com.guardiants.platform.commands.domain.model.events;

public record CommandDispatchedToDeviceEvent(Long commandId, Long vehicleId) {}
