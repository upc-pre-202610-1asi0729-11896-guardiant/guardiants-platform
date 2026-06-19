package com.guardiants.platform.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record RefreshSessionResource(@NotBlank String refreshToken) {}

