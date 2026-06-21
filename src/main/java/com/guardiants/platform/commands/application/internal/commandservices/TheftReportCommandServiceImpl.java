package com.guardiants.platform.commands.application.internal.commandservices;

import com.guardiants.platform.commands.application.commandservices.TheftReportCommandFailure;
import com.guardiants.platform.commands.application.commandservices.TheftReportCommandService;
import com.guardiants.platform.commands.application.internal.outboundservices.events.TheftReportEventPublisher;
import com.guardiants.platform.commands.domain.model.aggregates.TheftReport;
import com.guardiants.platform.commands.domain.model.commands.ReportTheftCommand;
import com.guardiants.platform.commands.domain.model.commands.ResolveTheftReportCommand;
import com.guardiants.platform.commands.domain.model.events.TheftReportRegisteredEvent;
import com.guardiants.platform.commands.domain.repositories.TheftReportRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class TheftReportCommandServiceImpl implements TheftReportCommandService {

    private final TheftReportRepository theftReportRepository;
    private final TheftReportEventPublisher theftReportEventPublisher;

    public TheftReportCommandServiceImpl(TheftReportRepository theftReportRepository,
                                         TheftReportEventPublisher theftReportEventPublisher) {
        this.theftReportRepository = theftReportRepository;
        this.theftReportEventPublisher = theftReportEventPublisher;
    }

    @Override
    public Result<TheftReport, TheftReportCommandFailure> handle(ReportTheftCommand command) {
        var report = new TheftReport(command);
        var saved = theftReportRepository.save(report);

        // publish event — EmergencyProtocolEventHandler activates the emergency protocol
        theftReportEventPublisher.publishTheftReported(
                new TheftReportRegisteredEvent(saved.getId(),
                        saved.getVehicleId(), saved.getReportedByUserId()));

        return Result.success(saved);
    }

    @Override
    public Result<TheftReport, TheftReportCommandFailure> handle(ResolveTheftReportCommand command) {
        return theftReportRepository.findById(command.reportId())
                .map(report -> {
                    report.resolve();
                    return Result.<TheftReport, TheftReportCommandFailure>success(
                            theftReportRepository.save(report));
                })
                .orElse(Result.failure(new TheftReportCommandFailure.TheftReportNotFound()));
    }
}
