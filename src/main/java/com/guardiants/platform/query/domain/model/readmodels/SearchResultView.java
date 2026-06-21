package com.guardiants.platform.query.domain.model.readmodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class SearchResultView {
    private SearchEntityType entityType;
    private Long entityId;
    private String label;
    private String subtitle;
    private Double lat;
    private Double lng;
    private Instant occurredAt;
}
