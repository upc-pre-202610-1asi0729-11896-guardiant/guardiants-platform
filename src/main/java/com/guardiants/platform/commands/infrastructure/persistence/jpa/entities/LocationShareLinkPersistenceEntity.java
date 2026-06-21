package com.guardiants.platform.commands.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "location_share_links")
public class LocationShareLinkPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private Long createdByUserId;

    @Column(nullable = false, unique = true, length = 100)
    private String token;

    @Column(name = "link_created_at", nullable = false)
    private Instant linkCreatedAt;

    @Column(nullable = false)
    private Instant expiresAt;
}
