package com.guardiants.platform.commands.interfaces.rest.resources;

import java.time.Instant;

public record LocationShareLinkResource(Long id, Long vehicleId, Long createdByUserId,
        String token, Instant createdAt, Instant expiresAt) {}
