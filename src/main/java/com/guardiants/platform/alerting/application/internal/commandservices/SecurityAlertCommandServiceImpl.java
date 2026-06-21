package com.guardiants.platform.alerting.application.internal.commandservices;

import com.guardiants.platform.alerting.application.commandservices.SecurityAlertCommandFailure;
import com.guardiants.platform.alerting.application.commandservices.SecurityAlertCommandService;
import com.guardiants.platform.alerting.application.internal.outboundservices.fcm.PushNotificationPort;
import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import com.guardiants.platform.alerting.domain.model.commands.AcknowledgeAlertCommand;
import com.guardiants.platform.alerting.domain.model.commands.CloseAlertCommand;
import com.guardiants.platform.alerting.domain.model.commands.GenerateSecurityAlertCommand;
import com.guardiants.platform.alerting.domain.model.events.AlertAcknowledgedEvent;
import com.guardiants.platform.alerting.domain.model.events.SecurityAlertGeneratedEvent;
import com.guardiants.platform.alerting.domain.repositories.NotificationPreferencesRepository;
import com.guardiants.platform.alerting.domain.repositories.SecurityAlertRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SecurityAlertCommandServiceImpl implements SecurityAlertCommandService {

    private final SecurityAlertRepository securityAlertRepository;
    private final NotificationPreferencesRepository notificationPreferencesRepository;
    private final PushNotificationPort pushNotificationPort;
    private final ApplicationEventPublisher eventPublisher;

    public SecurityAlertCommandServiceImpl(
            SecurityAlertRepository securityAlertRepository,
            NotificationPreferencesRepository notificationPreferencesRepository,
            PushNotificationPort pushNotificationPort,
            ApplicationEventPublisher eventPublisher) {
        this.securityAlertRepository = securityAlertRepository;
        this.notificationPreferencesRepository = notificationPreferencesRepository;
        this.pushNotificationPort = pushNotificationPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<SecurityAlert, SecurityAlertCommandFailure> handle(
            GenerateSecurityAlertCommand command) {
        var alert = new SecurityAlert(command);
        var saved = securityAlertRepository.save(alert);

        // send push only if owner has notifications enabled
        notificationPreferencesRepository.findByOwnerId(command.ownerId())
                .filter(prefs -> prefs.allows(command.type()))
                .ifPresent(prefs -> pushNotificationPort.sendAlertNotification(saved, command.ownerId()));

        eventPublisher.publishEvent(new SecurityAlertGeneratedEvent(
                saved.getId(), saved.getOwnerId(), saved.getVehicleId(), saved.getSeverity()));

        return Result.success(saved);
    }

    @Override
    public Result<SecurityAlert, SecurityAlertCommandFailure> handle(AcknowledgeAlertCommand command) {
        return securityAlertRepository.findById(command.alertId())
                .map(alert -> {
                    try {
                        alert.acknowledge();
                        var saved = securityAlertRepository.save(alert);
                        eventPublisher.publishEvent(new AlertAcknowledgedEvent(saved.getId()));
                        return Result.<SecurityAlert, SecurityAlertCommandFailure>success(saved);
                    } catch (IllegalStateException e) {
                        return Result.<SecurityAlert, SecurityAlertCommandFailure>failure(
                                new SecurityAlertCommandFailure.InvalidStatusTransition());
                    }
                })
                .orElse(Result.failure(new SecurityAlertCommandFailure.AlertNotFound()));
    }

    @Override
    public Result<SecurityAlert, SecurityAlertCommandFailure> handle(CloseAlertCommand command) {
        return securityAlertRepository.findById(command.alertId())
                .map(alert -> {
                    try {
                        alert.close();
                        return Result.<SecurityAlert, SecurityAlertCommandFailure>success(
                                securityAlertRepository.save(alert));
                    } catch (IllegalStateException e) {
                        return Result.<SecurityAlert, SecurityAlertCommandFailure>failure(
                                new SecurityAlertCommandFailure.InvalidStatusTransition());
                    }
                })
                .orElse(Result.failure(new SecurityAlertCommandFailure.AlertNotFound()));
    }
}
