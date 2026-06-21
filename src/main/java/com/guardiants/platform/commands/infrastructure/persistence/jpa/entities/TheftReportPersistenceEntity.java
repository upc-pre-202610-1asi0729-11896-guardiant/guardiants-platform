package com.guardiants.platform.commands.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "theft_reports")
public class TheftReportPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private Long reportedByUserId;

    @Column(nullable = false)
    private Instant reportedAt;

    @Column(nullable = false, length = 10)
    private String status;

    private Long relatedAlertId;
}
