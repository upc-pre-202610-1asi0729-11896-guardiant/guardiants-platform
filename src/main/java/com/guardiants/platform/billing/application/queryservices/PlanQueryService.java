package com.guardiants.platform.billing.application.queryservices;

import com.guardiants.platform.billing.domain.model.aggregates.Plan;
import com.guardiants.platform.billing.domain.model.queries.GetAllPlansQuery;
import com.guardiants.platform.billing.domain.model.queries.GetPlanByIdQuery;

import java.util.List;
import java.util.Optional;

public interface PlanQueryService {
    List<Plan> handle(GetAllPlansQuery query);
    Optional<Plan> handle(GetPlanByIdQuery query);
}
