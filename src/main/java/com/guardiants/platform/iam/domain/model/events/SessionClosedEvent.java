package com.guardiants.platform.iam.domain.model.events;

// SessionClosedEvent.java
public record SessionClosedEvent(Long sessionId, Long userId) {}