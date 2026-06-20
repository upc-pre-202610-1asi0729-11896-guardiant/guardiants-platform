package com.guardiants.platform.fleet.domain.model.aggregates;

import com.guardiants.platform.fleet.domain.model.valueobjects.VehicleStatus;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle extends AbstractDomainAggregateRoot<Vehicle> {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private VehicleStatus status = VehicleStatus.ACTIVE;

    /** Reconstruction constructor used by persistence assemblers. */
    public Vehicle(Long fleetId, String plate, String model, String brand,
                   int year, VehicleStatus status) {
        this.fleetId = fleetId;
        this.plate = plate;
        this.model = model;
        this.brand = brand;
        this.year = year;
        this.status = status;
    }

    public boolean isActive()           { return status == VehicleStatus.ACTIVE; }
    public boolean isAvailableForLoan() { return status == VehicleStatus.ACTIVE; }
}