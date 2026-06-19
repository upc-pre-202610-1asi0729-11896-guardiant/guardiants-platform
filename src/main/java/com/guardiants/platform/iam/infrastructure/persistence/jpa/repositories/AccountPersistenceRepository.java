// AccountPersistenceRepository.java
package com.guardiants.platform.iam.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.iam.infrastructure.persistence.jpa.entities.AccountPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountPersistenceRepository
        extends JpaRepository<AccountPersistenceEntity, Long> {
    Optional<AccountPersistenceEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}