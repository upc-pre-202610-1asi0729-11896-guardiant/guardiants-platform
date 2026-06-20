package com.guardiants.platform.fleet.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record RequestVehicleLoanResource(@NotNull Long vehicleId,
        @NotNull Long requestedByPersonnelId, Instant expectedReturnDate) {}
