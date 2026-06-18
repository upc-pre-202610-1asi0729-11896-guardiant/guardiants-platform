package com.guardiants.platform.iam.domain.model.events;

public record EmailVerifiedEvent(Long accountId, String email) {
}