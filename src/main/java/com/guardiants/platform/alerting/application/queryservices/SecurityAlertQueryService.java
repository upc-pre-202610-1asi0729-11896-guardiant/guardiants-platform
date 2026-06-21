package com.guardiants.platform.alerting.application.queryservices;

import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import com.guardiants.platform.alerting.domain.model.queries.GetSecurityAlertByIdQuery;
import com.guardiants.platform.alerting.domain.model.queries.GetSecurityAlertsByOwnerIdQuery;
import java.util.List;
import java.util.Optional;

public interface SecurityAlertQueryService {
    List<SecurityAlert> handle(GetSecurityAlertsByOwnerIdQuery query);
    Optional<SecurityAlert> handle(GetSecurityAlertByIdQuery query);
}
