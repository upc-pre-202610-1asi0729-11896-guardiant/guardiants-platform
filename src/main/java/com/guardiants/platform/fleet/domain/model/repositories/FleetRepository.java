package com.guardiants.platform.fleet.domain.model.repositories;

import com.guardiants.platform.fleet.domain.model.aggregates.Fleet;

import java.util.List;
import java.util.Optional;

// FleetRepository.java
public interface FleetRepository {
    Optional<Fleet> findById(Long id);
    List<Fleet> findAllByOwnerId(Long ownerId);
    Fleet save(Fleet fleet);
}