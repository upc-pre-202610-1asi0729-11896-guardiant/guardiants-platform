package com.guardiants.platform.alerting.application.internal.queryservices;

import com.guardiants.platform.alerting.application.queryservices.AlertRuleQueryService;
import com.guardiants.platform.alerting.domain.model.aggregates.AlertRule;
import com.guardiants.platform.alerting.domain.model.queries.GetAlertRulesByOwnerIdQuery;
import com.guardiants.platform.alerting.domain.repositories.AlertRuleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AlertRuleQueryServiceImpl implements AlertRuleQueryService {

    private final AlertRuleRepository alertRuleRepository;

    public AlertRuleQueryServiceImpl(AlertRuleRepository alertRuleRepository) {
        this.alertRuleRepository = alertRuleRepository;
    }

    @Override
    public List<AlertRule> handle(GetAlertRulesByOwnerIdQuery query) {
        return alertRuleRepository.findAllByOwnerId(query.ownerId());
    }
}
