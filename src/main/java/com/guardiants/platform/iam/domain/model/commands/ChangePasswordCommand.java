package com.guardiants.platform.iam.domain.model.commands;

public record ChangePasswordCommand(
        Long userId,
        String currentRawPassword,
        String newRawPassword) {
}