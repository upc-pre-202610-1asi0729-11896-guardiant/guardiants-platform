package com.guardiants.platform.iam.interfaces.rest.resources;
import java.time.Instant;
public record UserResource(Long id, Long accountId, String name, String email,
                           String profileType, UserPreferencesResource preferences,
                           Instant createdAt, Instant updatedAt) {}