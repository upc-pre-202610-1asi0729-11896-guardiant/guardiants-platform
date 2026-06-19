package com.guardiants.platform.iam.domain.model.events;

import com.guardiants.platform.iam.domain.model.valueobjects.ProfileType;

public record AccountRegisteredEvent(
        Long accountId,
        Long ownerId,
        String email,
        ProfileType profileType) {
}