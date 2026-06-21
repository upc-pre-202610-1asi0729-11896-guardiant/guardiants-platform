package com.guardiants.platform.commands.domain.model.events;

public record EngineBlockCommandIssuedEvent(Long commandId, Long vehicleId,
        Long triggeredByAlertId) {}
