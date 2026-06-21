package com.guardiants.platform.alerting.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.alerting.domain.model.entities.NotificationPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationPreferencesJpaRepository
        extends JpaRepository<NotificationPreferences, Long> {
    Optional<NotificationPreferences> findByOwnerId(Long ownerId);
}
