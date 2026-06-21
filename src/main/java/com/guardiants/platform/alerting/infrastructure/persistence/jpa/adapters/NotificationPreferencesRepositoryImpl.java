package com.guardiants.platform.alerting.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.alerting.domain.model.entities.NotificationPreferences;
import com.guardiants.platform.alerting.domain.repositories.NotificationPreferencesRepository;
import com.guardiants.platform.alerting.infrastructure.persistence.jpa.repositories.NotificationPreferencesJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class NotificationPreferencesRepositoryImpl implements NotificationPreferencesRepository {

    private final NotificationPreferencesJpaRepository jpaRepository;

    public NotificationPreferencesRepositoryImpl(NotificationPreferencesJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<NotificationPreferences> findByOwnerId(Long ownerId) {
        return jpaRepository.findByOwnerId(ownerId);
    }

    @Override
    public NotificationPreferences save(NotificationPreferences preferences) {
        return jpaRepository.save(preferences);
    }
}
