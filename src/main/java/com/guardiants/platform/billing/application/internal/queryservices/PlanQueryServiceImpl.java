package com.guardiants.platform.billing.application.internal.queryservices;

import com.guardiants.platform.billing.application.queryservices.PlanQueryService;
import com.guardiants.platform.billing.domain.model.aggregates.Plan;
import com.guardiants.platform.billing.domain.model.queries.GetAllPlansQuery;
import com.guardiants.platform.billing.domain.model.queries.GetPlanByIdQuery;
import com.guardiants.platform.billing.domain.repositories.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Plan> handle(GetPlanByIdQuery query) {
        return planRepository.findById(query.planId());
    }
}
