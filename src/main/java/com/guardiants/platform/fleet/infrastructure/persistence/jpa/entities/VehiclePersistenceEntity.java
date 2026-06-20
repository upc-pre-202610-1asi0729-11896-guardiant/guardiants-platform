package com.guardiants.platform.fleet.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vehicles")
public class VehiclePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long fleetId;

    @Column(nullable = false, unique = true, length = 20)
    private String plate;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, length = 15)
    private String status;
}
