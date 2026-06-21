package com.guardiants.platform.alerting.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "AlertingAlertRulePersistenceEntity")
@Table(name = "alert_rules")
public class AlertRulePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long ownerId;

    private Long vehicleId;

    @Column(nullable = false, length = 30)
    private String type;

    private Double centerLat;
    private Double centerLng;
    private Double radiusMeters;
    private Double speedThresholdKmh;
    private Integer prolongedStopThresholdMinutes;

    @Column(nullable = false)
    private boolean enabled;
}
