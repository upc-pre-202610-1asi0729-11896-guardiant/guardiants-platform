package com.guardiants.platform.fleet.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record RejectVehicleLoanResource(@NotBlank String reason) {}
