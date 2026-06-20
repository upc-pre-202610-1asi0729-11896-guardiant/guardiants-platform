package com.guardiants.platform.fleet.application.internal.commandservices;

import com.guardiants.platform.fleet.application.commandservices.AlertRuleCommandFailure;
import com.guardiants.platform.fleet.application.commandservices.AlertRuleCommandService;
import com.guardiants.platform.fleet.domain.model.aggregates.AlertRule;
import com.guardiants.platform.fleet.domain.model.commands.CreateAlertRuleCommand;
import com.guardiants.platform.fleet.domain.model.events.FleetAlertRuleConfiguredEvent;
import com.guardiants.platform.fleet.domain.repositories.AlertRuleRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class AlertRuleCommandServiceImpl implements AlertRuleCommandService {

    private final AlertRuleRepository alertRuleRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AlertRuleCommandServiceImpl(AlertRuleRepository alertRuleRepository,
                                       ApplicationEventPublisher eventPublisher) {
        this.alertRuleRepository = alertRuleRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<AlertRule, AlertRuleCommandFailure> handle(CreateAlertRuleCommand command) {
        try {
            var rule = new AlertRule(command);
            var saved = alertRuleRepository.save(rule);
            eventPublisher.publishEvent(
                    new FleetAlertRuleConfiguredEvent(saved.getId(),
                            saved.getFleetId(), saved.getType()));
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(new AlertRuleCommandFailure.InvalidGeofence());
        }
    }
}
