package com.guardiants.platform.commands.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "commands")
public class CommandPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private Long issuedByUserId;

    @Column(nullable = false, length = 20)
    private String type;

    private Long triggeredByAlertId;

    @Column(nullable = false, length = 15)
    private String status;

    @Column(nullable = false)
    private Instant issuedAt;

    private Instant dispatchedAt;
    private Instant acknowledgedAt;
    private Instant completedAt;

    @Column(length = 15)
    private String result;
}
