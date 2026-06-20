package com.guardiants.platform.fleet.application.queryservices;

import com.guardiants.platform.fleet.domain.model.aggregates.AlertRule;
import com.guardiants.platform.fleet.domain.model.queries.GetActiveAlertRulesByVehicleIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetAllAlertRulesByFleetIdQuery;

import java.util.List;

public interface AlertRuleQueryService {
    List<AlertRule> handle(GetAllAlertRulesByFleetIdQuery query);
    List<AlertRule> handle(GetActiveAlertRulesByVehicleIdQuery query);
}
