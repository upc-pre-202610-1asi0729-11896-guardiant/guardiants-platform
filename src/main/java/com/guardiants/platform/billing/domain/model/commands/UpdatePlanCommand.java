package com.guardiants.platform.billing.domain.model.commands;

public record UpdatePlanCommand(Long subscriptionId, Long newPlanId) {
}
