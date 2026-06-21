package com.guardiants.platform.alerting.application.commandservices;

public interface SecurityAlertCommandFailure {
    String messageKey();

    record AlertNotFound() implements SecurityAlertCommandFailure {
        public String messageKey() { return "alerting.error.alertNotFound"; }
    }

    record InvalidStatusTransition() implements SecurityAlertCommandFailure {
        public String messageKey() { return "alerting.error.invalidStatusTransition"; }
    }
}
