package com.guardiants.platform.iam.domain.model.commands;

public record UpdateProfileCommand(Long userId, String name, String email) {
}