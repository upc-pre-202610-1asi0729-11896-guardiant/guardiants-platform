package com.guardiants.platform.query.domain.model.queries;

import com.guardiants.platform.query.domain.model.readmodels.SearchEntityType;
import java.time.Instant;
import java.util.List;

public record SearchQuery(
        String queryText,
        List<SearchEntityType> entityTypes,
        Instant startDate,
        Instant endDate,
        String vehicleStatus,
        String eventType,
        Double lat,
        Double lng) {

    public boolean isEmpty() {
        return queryText == null || queryText.isBlank();
    }
}
