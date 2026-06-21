package com.guardiants.platform.query.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vehicle_operational_summary_views")
public class VehicleOperationalSummaryViewPersistenceEntity extends AuditableAbstractPersistenceEntity {

    private Long vehicleId;
    private String plate;
    private double totalDistanceKm;
    private int totalTripsCount;
    private int alertsCount;
    private int loansCount;
    private Double drivingScore;
}
