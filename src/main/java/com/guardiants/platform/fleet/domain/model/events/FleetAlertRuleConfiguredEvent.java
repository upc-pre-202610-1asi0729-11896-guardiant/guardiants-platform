package com.guardiants.platform.fleet.domain.model.events;

import com.guardiants.platform.fleet.domain.model.valueobjects.AlertRuleType;

public record FleetAlertRuleConfiguredEvent(Long ruleId, Long fleetId, AlertRuleType type) {}
