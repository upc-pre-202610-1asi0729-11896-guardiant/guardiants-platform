package com.guardiants.platform.query.domain.model.readmodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class RiskPatternView {
    private RiskPatternType type;
    private int occurrences;
    private Instant lastDetectedAt;
}
