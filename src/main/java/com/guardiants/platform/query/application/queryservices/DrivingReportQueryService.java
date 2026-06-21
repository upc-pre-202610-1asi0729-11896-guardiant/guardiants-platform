package com.guardiants.platform.query.application.queryservices;

import com.guardiants.platform.query.domain.model.readmodels.DrivingReportView;
import com.guardiants.platform.query.domain.model.queries.GetDrivingReportQuery;
import java.util.Optional;

public interface DrivingReportQueryService {
    Optional<DrivingReportView> handle(GetDrivingReportQuery query);
}
