package com.guardiants.platform.iam.application.commandservices;

public interface SessionCommandFailure {
    String messageKey();

    record InvalidCredentials() implements SessionCommandFailure {
        public String messageKey() { return "iam.error.invalidCredentials"; }
    }

    record SessionExpired() implements SessionCommandFailure {
        public String messageKey() { return "iam.error.invalidCredentials"; }
    }
}