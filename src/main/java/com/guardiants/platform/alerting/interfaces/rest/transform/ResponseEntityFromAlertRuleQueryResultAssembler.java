package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.domain.model.aggregates.AlertRule;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

public class ResponseEntityFromAlertRuleQueryResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromAlertRule(Optional<AlertRule> alertRule) {
        return alertRule
                .map(r -> ResponseEntity.ok(AlertRuleResourceFromEntityAssembler.toResourceFromEntity(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    public static ResponseEntity<?> toResponseEntityFromList(List<AlertRule> rules) {
        return ResponseEntity.ok(rules.stream()
                .map(AlertRuleResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }

    public static ResponseEntity<ProblemDetail> notFound(MessageSource messageSource,
                                                         String messageKey, Object... args) {
        var detail = messageSource.getMessage(messageKey, args, messageKey,
                LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detail));
    }
}
