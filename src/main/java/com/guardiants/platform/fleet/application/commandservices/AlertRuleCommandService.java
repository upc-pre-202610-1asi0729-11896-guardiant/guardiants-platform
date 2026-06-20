package com.guardiants.platform.fleet.application.commandservices;

import com.guardiants.platform.fleet.domain.model.aggregates.AlertRule;
import com.guardiants.platform.fleet.domain.model.commands.CreateAlertRuleCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface AlertRuleCommandService {
    Result<AlertRule, AlertRuleCommandFailure> handle(CreateAlertRuleCommand command);
}
