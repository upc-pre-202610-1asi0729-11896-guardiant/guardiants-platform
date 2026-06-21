package com.guardiants.platform.alerting.domain.repositories;

import com.guardiants.platform.alerting.domain.model.aggregates.AlertRule;
import java.util.List;
import java.util.Optional;

public interface AlertRuleRepository {
    Optional<AlertRule> findById(Long id);
    List<AlertRule> findAllByOwnerId(Long ownerId);
    AlertRule save(AlertRule rule);
}
