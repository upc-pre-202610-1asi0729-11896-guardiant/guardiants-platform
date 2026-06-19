package com.guardiants.platform.fleet.domain.model.repositories;


import com.guardiants.platform.fleet.domain.model.aggregates.Vehicle;

import java.util.List;
import java.util.Optional;

// VehicleRepository.java
public interface VehicleRepository {
    Optional<Vehicle> findById(Long id);
    List<Vehicle> findAllByFleetId(Long fleetId);
    Optional<Vehicle> findByPlate(String plate);
    boolean existsById(Long id);
    boolean existsByPlate(String plate);
    Vehicle save(Vehicle vehicle);
}