package com.guardiants.platform.fleet.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterVehicleResource(
        @NotNull Long fleetId,
        @NotBlank String plate,
        @NotBlank String model,
        @NotBlank String brand,
        @Min(1900) int year) {}
