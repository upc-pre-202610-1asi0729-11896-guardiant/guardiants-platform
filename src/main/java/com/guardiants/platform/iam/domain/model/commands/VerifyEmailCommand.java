package com.guardiants.platform.iam.domain.model.commands;

public record VerifyEmailCommand(Long accountId, String verificationToken) {
}