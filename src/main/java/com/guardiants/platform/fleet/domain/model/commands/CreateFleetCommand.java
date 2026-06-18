package com.guardiants.platform.fleet.domain.model.commands;

import com.guardiants.platform.fleet.domain.model.valueobjects.OrganizationType;

public record CreateFleetCommand(
        Long ownerId,
        String name,
        OrganizationType organizationType) {
}