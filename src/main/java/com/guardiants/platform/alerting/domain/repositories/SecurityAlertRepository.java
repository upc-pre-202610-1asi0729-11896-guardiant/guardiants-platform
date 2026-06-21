package com.guardiants.platform.alerting.domain.repositories;

import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import java.util.Optional;

public interface SecurityAlertRepository {
    Optional<SecurityAlert> findById(Long id);
    SecurityAlert save(SecurityAlert alert);
}
