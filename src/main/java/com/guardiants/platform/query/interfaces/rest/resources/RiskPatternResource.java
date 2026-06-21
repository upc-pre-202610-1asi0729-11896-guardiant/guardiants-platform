package com.guardiants.platform.query.interfaces.rest.resources;

import java.time.Instant;

public record RiskPatternResource(String type, int occurrences, Instant lastDetectedAt) {}
