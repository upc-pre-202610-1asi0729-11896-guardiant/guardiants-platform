package com.guardiants.platform.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterAccountResource(
        @NotBlank(message = "{validation.not-blank}")
        @Email(message = "{validation.email}")
        String email,

        @NotBlank(message = "{validation.not-blank}")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "{validation.not-blank}")
        String profileType,

        @NotBlank(message = "{validation.not-blank}")
        String name) {
}