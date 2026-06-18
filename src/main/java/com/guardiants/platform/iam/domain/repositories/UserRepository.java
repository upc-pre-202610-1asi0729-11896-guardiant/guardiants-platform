package com.guardiants.platform.iam.domain.repositories;

import com.guardiants.platform.iam.domain.model.aggregates.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByAccountId(Long accountId);
    boolean existsById(Long id);
    User save(User user);
}