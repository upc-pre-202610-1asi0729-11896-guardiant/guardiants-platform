package com.guardiants.platform.alerting.domain.repositories;

import com.guardiants.platform.alerting.domain.model.aggregates.AlertRule;
import java.util.Optional;

public interface AlertRuleRepository {
    Optional<AlertRule> findById(Long id);
    AlertRule save(AlertRule rule);
}
