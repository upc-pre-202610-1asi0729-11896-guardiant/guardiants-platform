package com.guardiants.platform.fleet.interfaces.rest.resources;

import java.time.Instant;

public record FleetResource(Long id, Long ownerId, String name,
                            String organizationType, Instant createdAt) {}
