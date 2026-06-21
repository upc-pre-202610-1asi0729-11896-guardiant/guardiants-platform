package com.guardiants.platform.commands.domain.model.events;

public record CommandAckReceivedEvent(Long commandId, Long vehicleId, boolean success) {}
