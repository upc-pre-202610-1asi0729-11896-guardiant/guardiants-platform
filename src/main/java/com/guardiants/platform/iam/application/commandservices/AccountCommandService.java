package com.guardiants.platform.iam.application.commandservices;

import com.guardiants.platform.iam.domain.model.aggregates.Account;
import com.guardiants.platform.iam.domain.model.commands.RegisterAccountCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface AccountCommandService {
    Result<Account, AccountCommandFailure> handle(RegisterAccountCommand command);
}