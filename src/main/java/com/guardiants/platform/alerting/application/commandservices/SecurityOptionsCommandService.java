package com.guardiants.platform.alerting.application.commandservices;

import com.guardiants.platform.alerting.domain.model.commands.UpdateSecurityOptionsCommand;
import com.guardiants.platform.alerting.domain.model.entities.SecurityOptions;
import com.guardiants.platform.shared.application.result.Result;

public interface SecurityOptionsCommandService {
    Result<SecurityOptions, SecurityOptionsCommandFailure> handle(UpdateSecurityOptionsCommand command);
}
