package com.guardiants.platform.query.application.internal.queryservices;

import com.guardiants.platform.query.application.queryservices.RouteHistoryQueryService;
import com.guardiants.platform.query.domain.model.readmodels.RouteHistoryView;
import com.guardiants.platform.query.domain.model.queries.GetRouteHistoryQuery;
import com.guardiants.platform.query.domain.repositories.RouteHistoryViewRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RouteHistoryQueryServiceImpl implements RouteHistoryQueryService {

    private final RouteHistoryViewRepository routeHistoryViewRepository;

    public RouteHistoryQueryServiceImpl(RouteHistoryViewRepository routeHistoryViewRepository) {
        this.routeHistoryViewRepository = routeHistoryViewRepository;
    }

    @Override
    public Optional<RouteHistoryView> handle(GetRouteHistoryQuery query) {
        return routeHistoryViewRepository.findByVehicleIdAndPeriod(
                query.vehicleId(), query.startDate(), query.endDate());
    }
}
