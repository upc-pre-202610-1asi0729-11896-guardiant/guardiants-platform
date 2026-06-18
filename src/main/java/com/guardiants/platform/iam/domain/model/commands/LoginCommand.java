package com.guardiants.platform.iam.domain.model.commands;

public record LoginCommand(String email, String rawPassword) {
}