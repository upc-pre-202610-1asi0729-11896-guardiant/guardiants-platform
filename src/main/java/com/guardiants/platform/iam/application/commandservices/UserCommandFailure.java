package com.guardiants.platform.iam.application.commandservices;

public interface UserCommandFailure {
    String messageKey();

    record PasswordMismatch() implements UserCommandFailure {
        public String messageKey() { return "iam.error.invalidCredentials"; }
    }

    record WeakPassword() implements UserCommandFailure {
        public String messageKey() { return "iam.error.invalidCredentials"; }
    }
}