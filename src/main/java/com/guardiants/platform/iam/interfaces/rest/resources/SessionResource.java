package com.guardiants.platform.iam.interfaces.rest.resources;
import java.time.Instant;
public record SessionResource(Long id, Long userId, String accessToken,
                              String refreshToken, Instant issuedAt, Instant expiresAt, String status) {}