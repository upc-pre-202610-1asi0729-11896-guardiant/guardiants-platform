package com.guardiants.platform.alerting.domain.repositories;

import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import java.util.List;
import java.util.Optional;

public interface SecurityAlertRepository {
    Optional<SecurityAlert> findById(Long id);
    List<SecurityAlert> findAllByOwnerId(Long ownerId);
    SecurityAlert save(SecurityAlert alert);
}
