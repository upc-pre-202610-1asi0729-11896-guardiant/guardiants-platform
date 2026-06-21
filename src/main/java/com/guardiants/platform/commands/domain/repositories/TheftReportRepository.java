package com.guardiants.platform.commands.domain.repositories;

import com.guardiants.platform.commands.domain.model.aggregates.TheftReport;
import java.util.List;
import java.util.Optional;

public interface TheftReportRepository {
    Optional<TheftReport> findById(Long id);
    List<TheftReport> findAllActive();
    TheftReport save(TheftReport report);
}
