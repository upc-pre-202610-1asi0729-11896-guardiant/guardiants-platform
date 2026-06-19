package com.guardiants.platform.iam.application.commandservices;

public interface AccountCommandFailure {
    String messageKey();

    record EmailAlreadyExists() implements AccountCommandFailure {
        public String messageKey() { return "iam.error.emailAlreadyExists"; }
    }

    record EmailNotVerified() implements AccountCommandFailure {
        public String messageKey() { return "iam.error.emailNotVerified"; }
    }

    record InvalidVerificationToken() implements AccountCommandFailure {
        public String messageKey() { return "iam.error.invalidCredentials"; }
    }
}