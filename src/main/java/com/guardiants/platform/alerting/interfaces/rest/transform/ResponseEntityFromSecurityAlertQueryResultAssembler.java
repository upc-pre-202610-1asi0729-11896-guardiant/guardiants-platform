package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

public class ResponseEntityFromSecurityAlertQueryResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromList(List<SecurityAlert> alerts) {
        return ResponseEntity.ok(alerts.stream()
                .map(SecurityAlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }

    public static ResponseEntity<?> toResponseEntityFromAlert(Optional<SecurityAlert> alert) {
        return alert
                .map(a -> ResponseEntity.ok(SecurityAlertResourceFromEntityAssembler.toResourceFromEntity(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    public static ResponseEntity<ProblemDetail> notFound(MessageSource messageSource,
                                                         String messageKey, Object... args) {
        var detail = messageSource.getMessage(messageKey, args, messageKey,
                LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detail));
    }
}
