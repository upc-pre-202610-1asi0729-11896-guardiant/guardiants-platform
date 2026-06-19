package com.guardiants.platform.billing.interfaces.rest.transform;

import com.guardiants.platform.billing.domain.model.aggregates.Subscription;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseEntityFromBillingQueryResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromSubscription(
            Optional<Subscription> subscription) {
        return subscription
                .map(s -> ResponseEntity.ok(
                        SubscriptionResourceFromEntityAssembler.toResourceFromEntity(s)))
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
