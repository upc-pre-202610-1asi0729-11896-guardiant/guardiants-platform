package com.guardiants.platform.alerting.application.internal.outboundservices.fcm;

import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;

public interface PushNotificationPort {
    void sendAlertNotification(SecurityAlert alert, Long ownerId);
}
