package com.guardiants.platform.iam.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.iam.infrastructure.persistence.jpa.entities.SessionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// SessionPersistenceRepository.java
@Repository
public interface SessionPersistenceRepository
        extends JpaRepository<SessionPersistenceEntity, Long> {
    Optional<SessionPersistenceEntity> findByRefreshToken(String refreshToken);
    Optional<SessionPersistenceEntity> findFirstByUserIdAndStatus(Long userId, String status);
}