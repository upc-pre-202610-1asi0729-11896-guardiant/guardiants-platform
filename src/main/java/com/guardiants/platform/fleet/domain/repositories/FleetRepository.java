package com.guardiants.platform.fleet.domain.repositories;

import com.guardiants.platform.fleet.domain.model.aggregates.Fleet;

import java.util.List;
import java.util.Optional;

public interface FleetRepository {
    Optional<Fleet> findById(Long id);
    List<Fleet> findAllByOwnerId(Long ownerId);
    Fleet save(Fleet fleet);
}
