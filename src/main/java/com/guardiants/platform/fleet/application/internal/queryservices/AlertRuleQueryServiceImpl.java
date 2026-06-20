package com.guardiants.platform.fleet.application.internal.queryservices;

import com.guardiants.platform.fleet.application.queryservices.AlertRuleQueryService;
import com.guardiants.platform.fleet.domain.model.aggregates.AlertRule;
import com.guardiants.platform.fleet.domain.model.queries.GetActiveAlertRulesByVehicleIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetAllAlertRulesByFleetIdQuery;
import com.guardiants.platform.fleet.domain.repositories.AlertRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertRuleQueryServiceImpl implements AlertRuleQueryService {

    private final AlertRuleRepository alertRuleRepository;

    public AlertRuleQueryServiceImpl(AlertRuleRepository alertRuleRepository) {
        this.alertRuleRepository = alertRuleRepository;
    }

    @Override
    public List<AlertRule> handle(GetAllAlertRulesByFleetIdQuery query) {
        return alertRuleRepository.findAllByFleetId(query.fleetId());
    }

    @Override
    public List<AlertRule> handle(GetActiveAlertRulesByVehicleIdQuery query) {
        return alertRuleRepository.findAllActiveByVehicleId(query.vehicleId());
    }
}
