package com.guardiants.platform.query.domain.repositories;

import com.guardiants.platform.query.domain.model.readmodels.SearchResultView;
import java.time.Instant;
import java.util.List;

public interface RouteSearchIndexRepository {
    List<SearchResultView> search(String queryText, Instant start, Instant end);
    void upsert(SearchResultView view);
}
