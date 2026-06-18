package com.guardiants.platform.iam.application.commandservices;

import com.guardiants.platform.iam.domain.model.aggregates.Session;
import com.guardiants.platform.iam.domain.model.commands.LoginCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface SessionCommandService {
    Result<Session, SessionCommandFailure> handle(LoginCommand command);
    Result<Session, SessionCommandFailure> handle(RefreshSessionCommand command);

}