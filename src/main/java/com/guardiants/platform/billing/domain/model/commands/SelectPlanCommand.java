package com.guardiants.platform.billing.domain.model.commands;

public record SelectPlanCommand(Long ownerId, Long planId) {
}