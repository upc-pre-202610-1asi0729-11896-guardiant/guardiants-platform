package com.guardiants.platform.commands.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record GenerateLocationShareLinkResource(@NotNull Long vehicleId,
        @NotNull Long createdByUserId, Instant expiresAt) {}
