package com.guardiants.platform.alerting.application.internal.commandservices;

import com.guardiants.platform.alerting.application.commandservices.AlertRuleCommandFailure;
import com.guardiants.platform.alerting.application.commandservices.AlertRuleCommandService;
import com.guardiants.platform.alerting.domain.model.aggregates.AlertRule;
import com.guardiants.platform.alerting.domain.model.commands.CreateAlertRuleCommand;
import com.guardiants.platform.alerting.domain.model.commands.UpdateAlertRuleCommand;
import com.guardiants.platform.alerting.domain.repositories.AlertRuleRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service("alertingAlertRuleCommandServiceImpl")
public class AlertRuleCommandServiceImpl implements AlertRuleCommandService {

    private final AlertRuleRepository alertRuleRepository;

    public AlertRuleCommandServiceImpl(AlertRuleRepository alertRuleRepository) {
        this.alertRuleRepository = alertRuleRepository;
    }

    @Override
    public Result<AlertRule, AlertRuleCommandFailure> handle(CreateAlertRuleCommand command) {
        try {
            var rule = new AlertRule(command);
            var saved = alertRuleRepository.save(rule);
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(new AlertRuleCommandFailure.InvalidGeofence());
        }
    }

    @Override
    public Result<AlertRule, AlertRuleCommandFailure> handle(UpdateAlertRuleCommand command) {
        return alertRuleRepository.findById(command.ruleId())
                .map(rule -> {
                    rule.update(command);
                    return Result.<AlertRule, AlertRuleCommandFailure>success(
                            alertRuleRepository.save(rule));
                })
                .orElse(Result.failure(new AlertRuleCommandFailure.AlertRuleNotFound()));
    }
}
