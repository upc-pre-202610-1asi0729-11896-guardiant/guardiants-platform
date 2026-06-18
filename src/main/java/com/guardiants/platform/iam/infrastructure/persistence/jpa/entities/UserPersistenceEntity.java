package com.guardiants.platform.iam.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    private String profileType;

    @Column(length = 5)
    private String language = "ES";

    @Column(length = 10)
    private String theme = "LIGHT";

    // getters + setters
}