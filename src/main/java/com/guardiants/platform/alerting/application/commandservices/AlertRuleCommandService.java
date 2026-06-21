package com.guardiants.platform.alerting.application.commandservices;

import com.guardiants.platform.alerting.domain.model.aggregates.AlertRule;
import com.guardiants.platform.alerting.domain.model.commands.CreateAlertRuleCommand;
import com.guardiants.platform.alerting.domain.model.commands.UpdateAlertRuleCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface AlertRuleCommandService {
    Result<AlertRule, AlertRuleCommandFailure> handle(CreateAlertRuleCommand command);
    Result<AlertRule, AlertRuleCommandFailure> handle(UpdateAlertRuleCommand command);
}
