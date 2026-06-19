package com.guardiants.platform.iam.domain.model.events;

// SessionStartedEvent.java
public record SessionStartedEvent(Long sessionId, Long userId) {}