package com.guardiants.platform.billing.application.internal.queryservices;

import com.guardiants.platform.billing.application.queryservices.PlanQueryService;
import com.guardiants.platform.billing.domain.model.aggregates.Plan;
import com.guardiants.platform.billing.domain.model.queries.GetAllPlansQuery;
import com.guardiants.platform.billing.domain.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanQueryServiceImpl implements PlanQueryService {

    private final PlanRepository planRepository;

    public PlanQueryServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public List<Plan> handle(GetAllPlansQuery query) {
        return planRepository.findAll();
    }
}
