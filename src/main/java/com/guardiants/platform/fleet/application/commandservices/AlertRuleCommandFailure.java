package com.guardiants.platform.fleet.application.commandservices;

public interface AlertRuleCommandFailure {
    String messageKey();

    record AlertRuleNotFound() implements AlertRuleCommandFailure {
        public String messageKey() { return "fleet.error.alertRuleNotFound"; }
    }

    record InvalidGeofence() implements AlertRuleCommandFailure {
        public String messageKey() { return "fleet.error.invalidGeofence"; }
    }
}
