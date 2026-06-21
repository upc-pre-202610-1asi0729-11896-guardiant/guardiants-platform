package com.guardiants.platform.query.domain.repositories;

import com.guardiants.platform.query.domain.model.readmodels.ReportExport;
import java.util.Optional;

public interface ReportExportRepository {
    Optional<ReportExport> findById(Long id);
    ReportExport save(ReportExport export);
}
