package com.guardiants.platform.telemetry.application.commandservices;

public interface TelemetryCommandFailure {
    String messageKey();

    record InvalidPayload() implements TelemetryCommandFailure {
        public String messageKey() {
            return "telemetry.error.invalidPayload";
        }
    }

    record VehicleNotFound() implements TelemetryCommandFailure {
        public String messageKey() {
            return "telemetry.error.vehicleNotFound";
        }
    }
}
