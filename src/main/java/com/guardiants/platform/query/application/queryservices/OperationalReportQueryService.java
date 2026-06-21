package com.guardiants.platform.query.application.queryservices;

import com.guardiants.platform.query.domain.model.readmodels.OperationalReportView;
import com.guardiants.platform.query.domain.model.queries.GetOperationalReportQuery;
import java.util.Optional;

public interface OperationalReportQueryService {
    Optional<OperationalReportView> handle(GetOperationalReportQuery query);
}
