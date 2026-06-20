package com.guardiants.platform.fleet.interfaces.rest.resources;

import jakarta.validation.constraints.Min;

public record UpdateVehicleResource(String model, String brand, @Min(1900) int year) {}
