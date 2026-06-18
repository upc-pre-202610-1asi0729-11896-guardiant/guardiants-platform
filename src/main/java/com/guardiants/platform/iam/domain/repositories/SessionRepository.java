package com.guardiants.platform.iam.domain.repositories;

import java.util.Optional;

public interface SessionRepository {
    Optional<Session> findById(Long id);
    Optional<Session> findByRefreshToken(String refreshToken);
    Optional<Session> findActiveByUserId(Long userId);
    Session save(Session session);
}