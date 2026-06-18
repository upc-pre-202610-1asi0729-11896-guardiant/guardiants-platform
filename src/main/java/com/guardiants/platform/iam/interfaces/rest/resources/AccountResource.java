package com.guardiants.platform.iam.interfaces.rest.resources;

import java.time.Instant;

public record AccountResource(
        Long id,
        String email,
        String profileType,
        boolean emailVerified,
        Instant createdAt) {
}