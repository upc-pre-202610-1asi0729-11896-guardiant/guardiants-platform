package com.guardiants.platform.iam.interfaces.rest.resources;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public record PasswordChangeResource(
        @NotBlank String currentPassword,
        @NotBlank @Size(min = 8) String newPassword) {}