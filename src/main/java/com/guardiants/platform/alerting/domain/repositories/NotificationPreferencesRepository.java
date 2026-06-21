package com.guardiants.platform.alerting.domain.repositories;

import com.guardiants.platform.alerting.domain.model.entities.NotificationPreferences;
import java.util.Optional;

public interface NotificationPreferencesRepository {
    Optional<NotificationPreferences> findByOwnerId(Long ownerId);
    NotificationPreferences save(NotificationPreferences preferences);
}
