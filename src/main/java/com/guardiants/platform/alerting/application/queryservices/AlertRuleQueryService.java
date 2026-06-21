package com.guardiants.platform.alerting.application.queryservices;

import com.guardiants.platform.alerting.domain.model.aggregates.AlertRule;
import com.guardiants.platform.alerting.domain.model.queries.GetAlertRulesByOwnerIdQuery;
import java.util.List;

public interface AlertRuleQueryService {
    List<AlertRule> handle(GetAlertRulesByOwnerIdQuery query);
}
