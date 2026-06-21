package com.guardiants.platform.alerting.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.alerting.domain.model.entities.SecurityOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityOptionsJpaRepository extends JpaRepository<SecurityOptions, Long> {
    Optional<SecurityOptions> findByOwnerId(Long ownerId);
}
