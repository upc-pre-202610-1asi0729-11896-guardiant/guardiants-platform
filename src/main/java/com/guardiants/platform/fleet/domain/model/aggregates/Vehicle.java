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

    public boolean isActive()           { return status == VehicleStatus.ACTIVE; }
    public boolean isAvailableForLoan() { return status == VehicleStatus.ACTIVE; }
}