package com.guardiants.platform.iam.interfaces.rest.transform;

import com.guardiants.platform.iam.application.commandservices.UserCommandFailure;
import com.guardiants.platform.iam.domain.model.aggregates.User;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromUserCommandResultAssembler {

    // usa result.fold()
    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<User, UserCommandFailure> result, MessageSource messageSource) {
        return result.fold(
                user -> ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(user)),
                failure -> {
                    var detail = messageSource.getMessage(failure.messageKey(), null,
                            failure.messageKey(), LocaleContextHolder.getLocale());
                    return ResponseEntity.badRequest()
                            .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail));
                });
    }
}
