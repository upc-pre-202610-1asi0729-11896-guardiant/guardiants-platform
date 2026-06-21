package com.guardiants.platform.commands.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "device_health")
public class DeviceHealthPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private Long vehicleId;

    @Column(length = 100)
    private String deviceSerial;

    private String model;
    private String imei;

    @Column(length = 15)
    private String status;

    private Instant lastConnectionAt;
    private Double batteryLevelPercent;
}
