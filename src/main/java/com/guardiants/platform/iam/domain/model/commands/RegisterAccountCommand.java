package com.guardiants.platform.iam.domain.model.commands;

import com.guardiants.platform.iam.domain.model.valueobjects.ProfileType;

public record RegisterAccountCommand(
        String email,
        String rawPassword,
        ProfileType profileType,
        String name) {
}