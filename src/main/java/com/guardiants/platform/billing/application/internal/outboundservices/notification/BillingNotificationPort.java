// BillingNotificationPort.java
package com.guardiants.platform.billing.application.internal.outboundservices.notification;

public interface BillingNotificationPort {
    void sendPaymentFailureEmail(Long ownerId, Long subscriptionId, String failureReason);
}