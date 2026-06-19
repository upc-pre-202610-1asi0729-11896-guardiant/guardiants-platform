package com.guardiants.platform.billing.interfaces.rest.transform;

import com.guardiants.platform.billing.application.commandservices.SubscriptionCommandFailure;
import com.guardiants.platform.billing.domain.model.aggregates.Subscription;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromSubscriptionCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Subscription, SubscriptionCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                sub -> new ResponseEntity<>(
                        SubscriptionResourceFromEntityAssembler.toResourceFromEntity(sub),
                        HttpStatus.CREATED),
                failure -> {
                    var detail = messageSource.getMessage(failure.messageKey(), null,
                            failure.messageKey(), LocaleContextHolder.getLocale());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail));
                });
    }
}