package com.guardiants.platform.fleet.interfaces.rest.transform;

import com.guardiants.platform.fleet.application.commandservices.AlertRuleCommandFailure;
import com.guardiants.platform.fleet.domain.model.aggregates.AlertRule;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromAlertRuleCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<AlertRule, AlertRuleCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                rule -> new ResponseEntity<>(
                        AlertRuleResourceFromEntityAssembler.toResourceFromEntity(rule),
                        HttpStatus.CREATED),
                failure -> {
                    var status = failure instanceof AlertRuleCommandFailure.AlertRuleNotFound
                            ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
                    var detail = messageSource.getMessage(
                            failure.messageKey(), null, failure.messageKey(),
                            LocaleContextHolder.getLocale());
                    return ResponseEntity.status(status)
                            .body(ProblemDetail.forStatusAndDetail(status, detail));
                });
    }
}
