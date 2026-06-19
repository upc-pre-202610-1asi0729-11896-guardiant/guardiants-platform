// PaymentEventPublisher.java
package com.guardiants.platform.billing.application.internal.outboundservices.events;
import com.guardiants.platform.billing.domain.model.events.*;

public interface PaymentEventPublisher {
    void publishPaymentSucceeded(PaymentSucceededEvent event);
    void publishPaymentFailed(PaymentFailedEvent event);
}