package com.guardiants.platform.fleet.domain.model.commands;

import java.time.Instant;

public record RequestVehicleLoanCommand(Long vehicleId, Long requestedByPersonnelId,
        Instant expectedReturnDate) {}
