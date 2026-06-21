package com.guardiants.platform.alerting.application.queryservices;

import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import com.guardiants.platform.alerting.domain.model.queries.GetSecurityAlertsByOwnerIdQuery;
import java.util.List;

public interface SecurityAlertQueryService {
    List<SecurityAlert> handle(GetSecurityAlertsByOwnerIdQuery query);
}
