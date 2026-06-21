package com.guardiants.platform.commands.domain.model.commands;

import java.time.Instant;

public record GenerateLocationShareLinkCommand(
        Long vehicleId,
        Long createdByUserId,
        Instant expiresAt) {}
