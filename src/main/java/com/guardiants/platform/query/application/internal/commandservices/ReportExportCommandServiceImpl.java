package com.guardiants.platform.query.application.internal.commandservices;

import com.guardiants.platform.query.application.commandservices.ReportExportCommandFailure;
import com.guardiants.platform.query.application.commandservices.ReportExportCommandService;
import com.guardiants.platform.query.application.internal.outboundservices.export.ReportExportPort;
import com.guardiants.platform.query.domain.model.commands.ExportReportCommand;
import com.guardiants.platform.query.domain.model.readmodels.ExportFormat;
import com.guardiants.platform.query.domain.model.readmodels.ReportExport;
import com.guardiants.platform.query.domain.repositories.ReportExportRepository;
import com.guardiants.platform.query.domain.repositories.RouteHistoryViewRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ReportExportCommandServiceImpl implements ReportExportCommandService {

    private final ReportExportRepository reportExportRepository;
    private final ReportExportPort reportExportPort;
    private final RouteHistoryViewRepository routeHistoryViewRepository;

    public ReportExportCommandServiceImpl(
            ReportExportRepository reportExportRepository,
            ReportExportPort reportExportPort,
            RouteHistoryViewRepository routeHistoryViewRepository) {
        this.reportExportRepository = reportExportRepository;
        this.reportExportPort = reportExportPort;
        this.routeHistoryViewRepository = routeHistoryViewRepository;
    }

    @Override
    public Result<ReportExport, ReportExportCommandFailure> handle(ExportReportCommand command) {
        // resolve the source view — extended to other report types in future iterations
        var sourceOpt = routeHistoryViewRepository.findById(command.sourceReportId());
        if (sourceOpt.isEmpty()) {
            return Result.failure(new ReportExportCommandFailure.SourceNotFound());
        }
        String url = command.format() == ExportFormat.PDF
                ? reportExportPort.exportToPdf(sourceOpt.get(), command.sourceReportId())
                : reportExportPort.exportToCsv(sourceOpt.get(), command.sourceReportId());

        var export = new ReportExport(command.sourceReportId(), command.format(), url);
        return Result.success(reportExportRepository.save(export));
    }
}
