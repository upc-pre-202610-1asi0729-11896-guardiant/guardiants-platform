package com.guardiants.platform.iam.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "sessions")
public class SessionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 1000)
    private String accessToken;

    @Column(nullable = false, length = 500)
    private String refreshToken;

    @Column(nullable = false)
    private Instant issuedAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false, length = 10)
    private String status;

}