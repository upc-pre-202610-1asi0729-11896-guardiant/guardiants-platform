package com.guardiants.platform.iam.interfaces.rest.resources;

public record VerifyEmailResource(Long accountId, String verificationToken) {
}