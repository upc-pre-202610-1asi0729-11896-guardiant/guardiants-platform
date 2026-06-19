package com.guardiants.platform.iam.application.commandservices;

import com.guardiants.platform.iam.domain.model.aggregates.User;
import com.guardiants.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.guardiants.platform.iam.domain.model.commands.UpdateProfileCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface UserCommandService {
    Result<User, UserCommandFailure> handle(UpdateProfileCommand command);

    Result<User, UserCommandFailure> handle(ChangePasswordCommand command);

    Result<User, UserCommandFailure> handle(ChangePasswordCommand command);
}