package com.guardiants.platform.commands.application.commandservices;

import com.guardiants.platform.commands.domain.model.aggregates.TheftReport;
import com.guardiants.platform.commands.domain.model.commands.ReportTheftCommand;
import com.guardiants.platform.commands.domain.model.commands.ResolveTheftReportCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface TheftReportCommandService {
    Result<TheftReport, TheftReportCommandFailure> handle(ReportTheftCommand command);
    Result<TheftReport, TheftReportCommandFailure> handle(ResolveTheftReportCommand command);
}
