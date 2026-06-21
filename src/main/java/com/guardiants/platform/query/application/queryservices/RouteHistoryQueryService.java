package com.guardiants.platform.query.application.queryservices;

import com.guardiants.platform.query.domain.model.readmodels.RouteHistoryView;
import com.guardiants.platform.query.domain.model.queries.GetRouteHistoryQuery;
import java.util.Optional;

public interface RouteHistoryQueryService {
    Optional<RouteHistoryView> handle(GetRouteHistoryQuery query);
}
