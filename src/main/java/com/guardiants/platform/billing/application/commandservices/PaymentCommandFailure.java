package com.guardiants.platform.billing.application.commandservices;

public interface PaymentCommandFailure {
    String messageKey();

    record Failed() implements PaymentCommandFailure {
        public String messageKey() { return "billing.error.paymentFailed"; }
    }
}
