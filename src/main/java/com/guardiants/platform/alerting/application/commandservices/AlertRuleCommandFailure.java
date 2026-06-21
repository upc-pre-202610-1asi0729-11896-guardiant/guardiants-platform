package com.guardiants.platform.alerting.application.commandservices;

public interface AlertRuleCommandFailure {
    String messageKey();

    record InvalidGeofence() implements AlertRuleCommandFailure {
        public String messageKey() {
            return "alerting.error.invalidGeofence";
        }
    }

    record AlertRuleNotFound() implements AlertRuleCommandFailure {
        public String messageKey() {
            return "alerting.error.alertRuleNotFound";
        }
    }
}
