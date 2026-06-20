package com.guardiants.platform.fleet.application.commandservices;

public interface VehicleCommandFailure {
    String messageKey();

    record VehicleNotFound() implements VehicleCommandFailure {
        public String messageKey() { return "fleet.error.vehicleNotFound"; }
    }

    record PlateAlreadyExists() implements VehicleCommandFailure {
        public String messageKey() { return "fleet.error.plateAlreadyExists"; }
    }

    record DeviceAlreadyAssigned() implements VehicleCommandFailure {
        public String messageKey() { return "fleet.error.deviceAlreadyAssigned"; }
    }

    record DeviceAssignmentNotFound() implements VehicleCommandFailure {
        public String messageKey() { return "fleet.error.deviceAssignmentNotFound"; }
    }
}
