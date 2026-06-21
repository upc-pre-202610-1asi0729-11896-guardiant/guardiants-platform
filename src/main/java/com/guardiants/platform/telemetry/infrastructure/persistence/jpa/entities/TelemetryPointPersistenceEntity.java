package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "telemetry_points")
public class TelemetryPointPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false, length = 100)
    private String deviceSerial;

    @Column(nullable = false)
    private Instant timestamp;

    private double lat;
    private double lng;

    private double speedKmh;
    private Double fuelLevelPercent;
    private Double engineTemperatureC;
    private double batteryLevelPercent;
    private Double odometerKm;
    private Integer rpm;
    private boolean engineOn;

    @Column(length = 20)
    private String connectivityStatus;

    private Instant connectivityLastSeenAt;
}
