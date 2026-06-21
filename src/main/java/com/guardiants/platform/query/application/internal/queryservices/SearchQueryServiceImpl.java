package com.guardiants.platform.query.application.internal.queryservices;

import com.guardiants.platform.query.application.queryservices.SearchQueryService;
import com.guardiants.platform.query.domain.model.readmodels.SearchEntityType;
import com.guardiants.platform.query.domain.model.readmodels.SearchResultView;
import com.guardiants.platform.query.domain.model.queries.SearchQuery;
import com.guardiants.platform.query.domain.repositories.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SearchQueryServiceImpl implements SearchQueryService {

    private final VehicleSearchIndexRepository vehicleSearchIndexRepository;
    private final RouteSearchIndexRepository routeSearchIndexRepository;
    private final SecurityEventSearchIndexRepository securityEventSearchIndexRepository;
    private final OperationalReportSearchIndexRepository operationalReportSearchIndexRepository;

    public SearchQueryServiceImpl(
            VehicleSearchIndexRepository vehicleSearchIndexRepository,
            RouteSearchIndexRepository routeSearchIndexRepository,
            SecurityEventSearchIndexRepository securityEventSearchIndexRepository,
            OperationalReportSearchIndexRepository operationalReportSearchIndexRepository) {
        this.vehicleSearchIndexRepository = vehicleSearchIndexRepository;
        this.routeSearchIndexRepository = routeSearchIndexRepository;
        this.securityEventSearchIndexRepository = securityEventSearchIndexRepository;
        this.operationalReportSearchIndexRepository = operationalReportSearchIndexRepository;
    }

    @Override
    public List<SearchResultView> handle(SearchQuery query) {
        if (query.isEmpty()) return List.of();

        var types = (query.entityTypes() == null || query.entityTypes().isEmpty())
                ? Arrays.asList(SearchEntityType.values())
                : query.entityTypes();

        var results = new ArrayList<SearchResultView>();

        if (types.contains(SearchEntityType.VEHICLE))
            results.addAll(vehicleSearchIndexRepository.search(
                    query.queryText(), query.vehicleStatus()));

        if (types.contains(SearchEntityType.ROUTE))
            results.addAll(routeSearchIndexRepository.search(
                    query.queryText(), query.startDate(), query.endDate()));

        if (types.contains(SearchEntityType.SECURITY_EVENT))
            results.addAll(securityEventSearchIndexRepository.search(
                    query.queryText(), query.eventType(),
                    query.startDate(), query.endDate()));

        if (types.contains(SearchEntityType.OPERATIONAL_REPORT))
            results.addAll(operationalReportSearchIndexRepository.search(query.queryText()));

        return results;
    }
}
