package com.guardiants.platform.alerting.infrastructure.fcm;

import com.guardiants.platform.alerting.application.internal.outboundservices.fcm.PushNotificationPort;
import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Firebase Cloud Messaging push adapter. In dev it logs the notification; in production it
 * would dispatch to FCM using the Firebase Admin SDK and the owner's registered device tokens.
 */
@Slf4j
@Component
public class FcmPushNotificationAdapter implements PushNotificationPort {

    @Override
    public void sendAlertNotification(SecurityAlert alert, Long ownerId) {
        log.info("FCM STUB — push security alert id={} severity={} to ownerId={}",
                alert.getId(), alert.getSeverity(), ownerId);
    }
}
