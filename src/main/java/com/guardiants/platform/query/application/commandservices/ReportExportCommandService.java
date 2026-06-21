package com.guardiants.platform.query.application.commandservices;

import com.guardiants.platform.query.domain.model.commands.ExportReportCommand;
import com.guardiants.platform.query.domain.model.readmodels.ReportExport;
import com.guardiants.platform.shared.application.result.Result;

public interface ReportExportCommandService {
    Result<ReportExport, ReportExportCommandFailure> handle(ExportReportCommand command);
}
