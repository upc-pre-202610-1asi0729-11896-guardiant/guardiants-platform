// SubscriptionEventPublisher.java
package com.guardiants.platform.billing.application.internal.outboundservices.events;
import com.guardiants.platform.billing.domain.model.events.*;

public interface SubscriptionEventPublisher {
    void publishSubscriptionActivated(SubscriptionActivatedEvent event);
    void publishSubscriptionSuspended(SubscriptionSuspendedEvent event);
    void publishSubscriptionExpired(SubscriptionExpiredEvent event);
}