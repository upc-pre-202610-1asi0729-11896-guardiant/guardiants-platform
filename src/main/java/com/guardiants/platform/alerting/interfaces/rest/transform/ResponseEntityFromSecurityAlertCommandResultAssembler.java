package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.application.commandservices.SecurityAlertCommandFailure;
import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromSecurityAlertCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<SecurityAlert, SecurityAlertCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                alert -> new ResponseEntity<>(
                        SecurityAlertResourceFromEntityAssembler.toResourceFromEntity(alert),
                        HttpStatus.CREATED),
                failure -> {
                    var status = failure instanceof SecurityAlertCommandFailure.AlertNotFound
                            ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
                    var detail = messageSource.getMessage(
                            failure.messageKey(), null, failure.messageKey(),
                            LocaleContextHolder.getLocale());
                    return ResponseEntity.status(status)
                            .body(ProblemDetail.forStatusAndDetail(status, detail));
                });
    }
}
