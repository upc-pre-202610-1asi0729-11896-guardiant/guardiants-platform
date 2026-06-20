package com.guardiants.platform.fleet.interfaces.rest;

import java.time.Instant;

// FleetResource.java
public record FleetResource(Long id, Long ownerId, String name,
                            String organizationType, Instant createdAt) {}
