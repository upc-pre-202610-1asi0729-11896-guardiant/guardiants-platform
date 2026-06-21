package com.guardiants.platform.alerting.domain.repositories;

import com.guardiants.platform.alerting.domain.model.entities.SecurityOptions;
import java.util.Optional;

public interface SecurityOptionsRepository {
    Optional<SecurityOptions> findByOwnerId(Long ownerId);
    SecurityOptions save(SecurityOptions options);
}
