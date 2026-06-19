package com.guardiants.platform.billing.domain.repositories;

import com.guardiants.platform.billing.domain.model.aggregates.Plan;
import java.util.List;
import java.util.Optional;

public interface PlanRepository {
    Optional<Plan> findById(Long id);
    List<Plan> findAll();
    Plan save(Plan plan);
}