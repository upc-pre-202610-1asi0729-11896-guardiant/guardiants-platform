package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.application.commandservices.NotificationPreferencesCommandFailure;
import com.guardiants.platform.alerting.domain.model.entities.NotificationPreferences;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromNotificationPreferencesCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<NotificationPreferences, NotificationPreferencesCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                prefs -> ResponseEntity.ok(
                        NotificationPreferencesResourceFromEntityAssembler.toResourceFromEntity(prefs)),
                failure -> {
                    var detail = messageSource.getMessage(
                            failure.messageKey(), null, failure.messageKey(),
                            LocaleContextHolder.getLocale());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail));
                });
    }
}
