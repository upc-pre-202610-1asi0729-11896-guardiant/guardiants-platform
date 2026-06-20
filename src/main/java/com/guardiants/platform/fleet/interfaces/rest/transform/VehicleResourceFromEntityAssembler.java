package com.guardiants.platform.fleet.interfaces.rest.transform;

import com.guardiants.platform.fleet.domain.model.aggregates.Vehicle;
import com.guardiants.platform.fleet.domain.model.entities.DeviceAssignment;
import com.guardiants.platform.fleet.interfaces.rest.resources.DeviceAssignmentResource;
import com.guardiants.platform.fleet.interfaces.rest.resources.VehicleResource;

import java.util.Optional;

public class VehicleResourceFromEntityAssembler {

    public static VehicleResource toResourceFromEntity(Vehicle vehicle,
                                                       Optional<DeviceAssignment> assignment) {
        var assignmentResource = assignment
                .map(a -> new DeviceAssignmentResource(a.getId(), a.getVehicleId(),
                        a.getDeviceSerial(), a.getAssignedAt(), a.getUnassignedAt()))
                .orElse(null);
        return new VehicleResource(
                vehicle.getId(), vehicle.getFleetId(), vehicle.getPlate(),
                vehicle.getModel(), vehicle.getBrand(), vehicle.getYear(),
                vehicle.getStatus().name(), assignmentResource, null);
    }
}
