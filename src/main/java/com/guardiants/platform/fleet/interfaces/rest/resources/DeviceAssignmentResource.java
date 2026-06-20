package com.guardiants.platform.fleet.interfaces.rest.resources;

import java.time.Instant;

public record DeviceAssignmentResource(Long id, Long vehicleId, String deviceSerial,
                                       Instant assignedAt, Instant unassignedAt) {}
