package com.guardiants.platform.commands.domain.model.events;

public record EngineBlockedEvent(Long vehicleId, Long commandId) {}
