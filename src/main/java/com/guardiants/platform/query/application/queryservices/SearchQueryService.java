package com.guardiants.platform.query.application.queryservices;

import com.guardiants.platform.query.domain.model.readmodels.SearchResultView;
import com.guardiants.platform.query.domain.model.queries.SearchQuery;
import java.util.List;

public interface SearchQueryService {
    List<SearchResultView> handle(SearchQuery query);
}
