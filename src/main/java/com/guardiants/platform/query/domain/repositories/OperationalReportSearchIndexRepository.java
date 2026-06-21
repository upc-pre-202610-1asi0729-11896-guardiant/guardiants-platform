package com.guardiants.platform.query.domain.repositories;

import com.guardiants.platform.query.domain.model.readmodels.SearchResultView;
import java.util.List;

public interface OperationalReportSearchIndexRepository {
    List<SearchResultView> search(String queryText);
    void upsert(SearchResultView view);
}
