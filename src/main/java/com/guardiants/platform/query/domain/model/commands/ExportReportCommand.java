package com.guardiants.platform.query.domain.model.commands;

import com.guardiants.platform.query.domain.model.readmodels.ExportFormat;

public record ExportReportCommand(Long sourceReportId, ExportFormat format) {
}
