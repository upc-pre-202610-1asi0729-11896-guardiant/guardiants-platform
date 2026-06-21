package com.guardiants.platform.alerting.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.alerting.domain.model.entities.SecurityOptions;
import com.guardiants.platform.alerting.domain.repositories.SecurityOptionsRepository;
import com.guardiants.platform.alerting.infrastructure.persistence.jpa.repositories.SecurityOptionsJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class SecurityOptionsRepositoryImpl implements SecurityOptionsRepository {

    private final SecurityOptionsJpaRepository jpaRepository;

    public SecurityOptionsRepositoryImpl(SecurityOptionsJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<SecurityOptions> findByOwnerId(Long ownerId) {
        return jpaRepository.findByOwnerId(ownerId);
    }

    @Override
    public SecurityOptions save(SecurityOptions options) {
        return jpaRepository.save(options);
    }
}
