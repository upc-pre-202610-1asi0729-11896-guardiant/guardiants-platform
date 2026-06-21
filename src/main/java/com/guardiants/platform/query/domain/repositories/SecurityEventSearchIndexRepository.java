package com.guardiants.platform.query.domain.repositories;

import com.guardiants.platform.query.domain.model.readmodels.SearchResultView;
import java.time.Instant;
import java.util.List;

public interface SecurityEventSearchIndexRepository {
    List<SearchResultView> search(String queryText, String eventType, Instant start, Instant end);
    void upsert(SearchResultView view);
}
