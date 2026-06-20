package com.guardiants.platform.fleet.domain.model.aggregates;

import com.guardiants.platform.fleet.domain.model.commands.RegisterVehicleCommand;
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

    public Vehicle(RegisterVehicleCommand command) {
        this.fleetId = command.fleetId();
        this.plate = command.plate();
        this.model = command.model();
        this.brand = command.brand();
        this.year = command.year();
        this.status = VehicleStatus.ACTIVE;
    }

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

    public void updateInformation(String model, String brand, int year) {
        if (model != null && !model.isBlank()) this.model = model;
        if (brand != null && !brand.isBlank()) this.brand = brand;
        if (year > 1900) this.year = year;
    }

    public void deactivate() { this.status = VehicleStatus.INACTIVE; }

    public void markOnLoan()    { this.status = VehicleStatus.ON_LOAN; }
    public void markAvailable() { this.status = VehicleStatus.ACTIVE; }

    public boolean isActive()           { return status == VehicleStatus.ACTIVE; }
    public boolean isAvailableForLoan() { return status == VehicleStatus.ACTIVE; }
}