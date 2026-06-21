package com.guardiants.platform.alerting.application.internal.commandservices;

import com.guardiants.platform.alerting.application.commandservices.NotificationPreferencesCommandFailure;
import com.guardiants.platform.alerting.application.commandservices.NotificationPreferencesCommandService;
import com.guardiants.platform.alerting.domain.model.commands.UpdateNotificationPreferencesCommand;
import com.guardiants.platform.alerting.domain.model.entities.NotificationPreferences;
import com.guardiants.platform.alerting.domain.repositories.NotificationPreferencesRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class NotificationPreferencesCommandServiceImpl
        implements NotificationPreferencesCommandService {

    private final NotificationPreferencesRepository notificationPreferencesRepository;

    public NotificationPreferencesCommandServiceImpl(
            NotificationPreferencesRepository notificationPreferencesRepository) {
        this.notificationPreferencesRepository = notificationPreferencesRepository;
    }

    @Override
    public Result<NotificationPreferences, NotificationPreferencesCommandFailure> handle(
            UpdateNotificationPreferencesCommand command) {
        var prefs = notificationPreferencesRepository.findByOwnerId(command.ownerId())
                .orElse(new NotificationPreferences(command.ownerId()));
        prefs.update(command);
        return Result.success(notificationPreferencesRepository.save(prefs));
    }
}
