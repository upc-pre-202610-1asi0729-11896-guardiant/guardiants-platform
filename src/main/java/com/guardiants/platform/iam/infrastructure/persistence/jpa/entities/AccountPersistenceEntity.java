package com.guardiants.platform.iam.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class AccountPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, length = 20)
    private String profileType;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(length = 100)
    private String verificationToken;

    // getters + setters
}