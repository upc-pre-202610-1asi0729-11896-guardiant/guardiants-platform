package com.guardiants.platform.fleet.interfaces.rest.resources;

import java.time.Instant;

public record VehicleLoanResource(Long id, Long vehicleId, Long fleetId,
        Long requestedByPersonnelId, Long approvedByApproverId, String status,
        Instant requestedAt, Instant decidedAt, Instant assignedAt,
        Instant returnRequestedAt, Instant returnConfirmedAt,
        String rejectionReason, Instant expectedReturnDate) {}
