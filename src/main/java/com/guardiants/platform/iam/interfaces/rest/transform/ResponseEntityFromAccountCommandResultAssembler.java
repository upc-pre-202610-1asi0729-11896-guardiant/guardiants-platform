package com.guardiants.platform.iam.interfaces.rest.transform;

import com.guardiants.platform.iam.application.commandservices.AccountCommandFailure;
import com.guardiants.platform.iam.domain.model.aggregates.Account;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromAccountCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Account, AccountCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                account -> new ResponseEntity<>(
                        AccountResourceFromEntityAssembler.toResourceFromEntity(account),
                        HttpStatus.CREATED),
                failure -> {
                    var detail = messageSource.getMessage(
                            failure.messageKey(), null, failure.messageKey(),
                            LocaleContextHolder.getLocale());
                    var status = failure instanceof AccountCommandFailure.EmailAlreadyExists
                            ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
                    return ResponseEntity.status(status)
                            .body(ProblemDetail.forStatusAndDetail(status, detail));
                });
    }
}