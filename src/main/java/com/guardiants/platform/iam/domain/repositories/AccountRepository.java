package com.guardiants.platform.iam.domain.repositories;

import com.guardiants.platform.iam.domain.model.aggregates.Account;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(Long id);
    Optional<Account> findByEmail(String email);
    boolean existsByEmail(String email);
    Account save(Account account);
}