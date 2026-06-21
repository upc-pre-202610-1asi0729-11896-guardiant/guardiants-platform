package com.guardiants.platform.commands.application.queryservices;

import com.guardiants.platform.commands.domain.model.aggregates.TheftReport;
import com.guardiants.platform.commands.domain.model.queries.GetActiveTheftReportsQuery;
import java.util.List;

public interface TheftReportQueryService {
    List<TheftReport> handle(GetActiveTheftReportsQuery query);
}
