package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "vehicle_general_statuses")
public class VehicleGeneralStatusPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private Long vehicleId;

    private Long latestPointId;

    @Column(nullable = false)
    private int activeAlertCount;

    @Column(nullable = false)
    private boolean isLocked;

    @Column(nullable = false)
    private Instant lastUpdatedAt;
}
