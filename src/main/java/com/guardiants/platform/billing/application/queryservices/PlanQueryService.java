package com.guardiants.platform.billing.application.queryservices;

import com.guardiants.platform.billing.domain.model.aggregates.Plan;
import com.guardiants.platform.billing.domain.model.queries.GetAllPlansQuery;

import java.util.List;

public interface PlanQueryService {
    List<Plan> handle(GetAllPlansQuery query);
}
