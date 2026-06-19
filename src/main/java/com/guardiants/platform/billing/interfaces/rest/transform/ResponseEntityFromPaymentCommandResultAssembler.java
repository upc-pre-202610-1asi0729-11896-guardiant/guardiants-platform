package com.guardiants.platform.billing.interfaces.rest.transform;

import com.guardiants.platform.billing.application.commandservices.PaymentCommandFailure;
import com.guardiants.platform.billing.domain.model.aggregates.Payment;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromPaymentCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Payment, PaymentCommandFailure> result, MessageSource messageSource) {
        return result.fold(
                payment -> ResponseEntity.ok(
                        PaymentResourceFromEntityAssembler.toResourceFromEntity(payment)),
                failure -> {
                    var detail = messageSource.getMessage(failure.messageKey(), null,
                            failure.messageKey(), LocaleContextHolder.getLocale());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail));
                });
    }
}
