package com.guardiants.platform.alerting.application.commandservices;

import com.guardiants.platform.alerting.domain.model.commands.UpdateNotificationPreferencesCommand;
import com.guardiants.platform.alerting.domain.model.entities.NotificationPreferences;
import com.guardiants.platform.shared.application.result.Result;

public interface NotificationPreferencesCommandService {
    Result<NotificationPreferences, NotificationPreferencesCommandFailure>
            handle(UpdateNotificationPreferencesCommand command);
}
