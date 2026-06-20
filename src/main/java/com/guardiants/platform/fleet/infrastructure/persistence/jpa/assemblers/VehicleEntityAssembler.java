package com.guardiants.platform.fleet.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.fleet.domain.model.aggregates.Vehicle;
import com.guardiants.platform.fleet.domain.model.valueobjects.VehicleStatus;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.entities.VehiclePersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleEntityAssembler {

    public VehiclePersistenceEntity toPersistenceEntityFromDomain(Vehicle vehicle) {
        var entity = new VehiclePersistenceEntity();
        entity.setId(vehicle.getId());
        entity.setFleetId(vehicle.getFleetId());
        entity.setPlate(vehicle.getPlate());
        entity.setModel(vehicle.getModel());
        entity.setBrand(vehicle.getBrand());
        entity.setYear(vehicle.getYear());
        entity.setStatus(vehicle.getStatus().name());
        return entity;
    }

    public Vehicle toDomainFromPersistenceEntity(VehiclePersistenceEntity entity) {
        var vehicle = new Vehicle(
                entity.getFleetId(),
                entity.getPlate(),
                entity.getModel(),
                entity.getBrand(),
                entity.getYear(),
                VehicleStatus.valueOf(entity.getStatus()));
        vehicle.setId(entity.getId());
        return vehicle;
    }
}
