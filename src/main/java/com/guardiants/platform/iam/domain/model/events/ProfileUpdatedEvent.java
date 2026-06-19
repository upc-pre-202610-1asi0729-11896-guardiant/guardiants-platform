package com.guardiants.platform.iam.domain.model.events;

public record ProfileUpdatedEvent(Long userId, String email) {}

