package com.guardiants.platform.billing.application.commandservices;

public interface SubscriptionCommandFailure {
    String messageKey();

    record NotFound() implements SubscriptionCommandFailure {
        public String messageKey() { return "billing.error.subscriptionNotFound"; }
    }

    record PlanNotFound() implements SubscriptionCommandFailure {
        public String messageKey() { return "billing.error.subscriptionNotFound"; }
    }

    record InvalidStatusTransition() implements SubscriptionCommandFailure {
        public String messageKey() { return "billing.error.subscriptionNotFound"; }
    }
}