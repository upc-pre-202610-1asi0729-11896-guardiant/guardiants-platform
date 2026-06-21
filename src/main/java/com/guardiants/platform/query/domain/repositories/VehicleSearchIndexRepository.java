package com.guardiants.platform.query.domain.repositories;

import com.guardiants.platform.query.domain.model.readmodels.SearchResultView;
import java.util.List;

public interface VehicleSearchIndexRepository {
    List<SearchResultView> search(String queryText, String vehicleStatus);
    void upsert(SearchResultView view);
}
