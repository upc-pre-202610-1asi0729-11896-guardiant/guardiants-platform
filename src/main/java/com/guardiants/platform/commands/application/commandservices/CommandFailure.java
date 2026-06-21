package com.guardiants.platform.commands.application.commandservices;

public interface CommandFailure {
    String messageKey();

    record VehicleNotFound() implements CommandFailure {
        public String messageKey() { return "commands.error.vehicleNotFound"; }
    }
    record DeviceUnreachable() implements CommandFailure {
        public String messageKey() { return "commands.error.deviceUnreachable"; }
    }
    record InvalidStatusTransition() implements CommandFailure {
        public String messageKey() { return "commands.error.invalidStatusTransition"; }
    }
}
