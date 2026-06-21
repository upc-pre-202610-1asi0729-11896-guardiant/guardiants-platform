package com.guardiants.platform.commands.application.internal.queryservices;

import com.guardiants.platform.commands.application.queryservices.TheftReportQueryService;
import com.guardiants.platform.commands.domain.model.aggregates.TheftReport;
import com.guardiants.platform.commands.domain.model.queries.GetActiveTheftReportsQuery;
import com.guardiants.platform.commands.domain.repositories.TheftReportRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TheftReportQueryServiceImpl implements TheftReportQueryService {

    private final TheftReportRepository theftReportRepository;

    public TheftReportQueryServiceImpl(TheftReportRepository theftReportRepository) {
        this.theftReportRepository = theftReportRepository;
    }

    @Override
    public List<TheftReport> handle(GetActiveTheftReportsQuery query) {
        return theftReportRepository.findAllActive();
    }
}
