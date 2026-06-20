package com.guardiants.platform.fleet.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateFleetResource(
        @NotNull Long ownerId,
        @NotBlank String name,
        @NotBlank String organizationType) {}
