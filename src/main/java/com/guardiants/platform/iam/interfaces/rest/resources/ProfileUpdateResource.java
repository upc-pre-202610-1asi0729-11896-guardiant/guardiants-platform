package com.guardiants.platform.iam.interfaces.rest.resources;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
public record ProfileUpdateResource(@NotBlank String name, @Email String email) {}