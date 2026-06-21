package com.guardiants.platform.query.domain.repositories;

import com.guardiants.platform.query.domain.model.readmodels.OperationalReportView;
import java.time.Instant;
import java.util.Optional;

public interface OperationalReportViewRepository {
    Optional<OperationalReportView> findByFleetIdAndPeriod(Long fleetId, Instant start, Instant end);
    OperationalReportView save(OperationalReportView view);
}
