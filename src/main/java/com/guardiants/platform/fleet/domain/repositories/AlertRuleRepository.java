package com.guardiants.platform.fleet.domain.repositories;

import com.guardiants.platform.fleet.domain.model.aggregates.AlertRule;

import java.util.List;
import java.util.Optional;

public interface AlertRuleRepository {
    Optional<AlertRule> findById(Long id);
    List<AlertRule> findAllByFleetId(Long fleetId);
    List<AlertRule> findAllActiveByVehicleId(Long vehicleId);
    AlertRule save(AlertRule rule);
}
