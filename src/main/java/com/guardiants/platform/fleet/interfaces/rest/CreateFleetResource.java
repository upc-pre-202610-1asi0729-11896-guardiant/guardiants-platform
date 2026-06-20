package com.guardiants.platform.fleet.interfaces.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateFleetResource(
        @NotNull Long ownerId,
        @NotBlank String name,
        @NotBlank String organizationType) {}

