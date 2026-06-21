package com.guardiants.platform.query.application.internal.queryservices;

import com.guardiants.platform.query.application.queryservices.OperationalReportQueryService;
import com.guardiants.platform.query.domain.model.readmodels.OperationalReportView;
import com.guardiants.platform.query.domain.model.queries.GetOperationalReportQuery;
import com.guardiants.platform.query.domain.repositories.OperationalReportViewRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class OperationalReportQueryServiceImpl implements OperationalReportQueryService {

    private final OperationalReportViewRepository operationalReportViewRepository;

    public OperationalReportQueryServiceImpl(
            OperationalReportViewRepository operationalReportViewRepository) {
        this.operationalReportViewRepository = operationalReportViewRepository;
    }

    @Override
    public Optional<OperationalReportView> handle(GetOperationalReportQuery query) {
        return operationalReportViewRepository.findByFleetIdAndPeriod(
                query.fleetId(), query.startDate(), query.endDate());
    }
}
