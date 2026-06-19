package com.guardiants.platform.billing.infrastructure.notification;

import com.guardiants.platform.billing.application.internal.outboundservices.notification.BillingNotificationPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailBillingNotificationAdapter implements BillingNotificationPort {
    @Override
    public void sendPaymentFailureEmail(Long ownerId, Long subscriptionId, String failureReason) {
        log.warn("BILLING EMAIL — ownerId={} sub={} reason={}", ownerId, subscriptionId, failureReason);
    }
}