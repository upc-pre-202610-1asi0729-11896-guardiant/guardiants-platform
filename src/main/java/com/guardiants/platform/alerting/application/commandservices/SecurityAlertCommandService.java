package com.guardiants.platform.alerting.application.commandservices;

import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import com.guardiants.platform.alerting.domain.model.commands.AcknowledgeAlertCommand;
import com.guardiants.platform.alerting.domain.model.commands.GenerateSecurityAlertCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface SecurityAlertCommandService {
    Result<SecurityAlert, SecurityAlertCommandFailure> handle(GenerateSecurityAlertCommand command);
    Result<SecurityAlert, SecurityAlertCommandFailure> handle(AcknowledgeAlertCommand command);
}
