package com.guardiants.platform.query.application.internal.queryservices;

import com.guardiants.platform.query.application.queryservices.DrivingReportQueryService;
import com.guardiants.platform.query.domain.model.readmodels.DrivingReportView;
import com.guardiants.platform.query.domain.model.queries.GetDrivingReportQuery;
import com.guardiants.platform.query.domain.repositories.DrivingReportViewRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DrivingReportQueryServiceImpl implements DrivingReportQueryService {

    private final DrivingReportViewRepository drivingReportViewRepository;

    public DrivingReportQueryServiceImpl(DrivingReportViewRepository drivingReportViewRepository) {
        this.drivingReportViewRepository = drivingReportViewRepository;
    }

    @Override
    public Optional<DrivingReportView> handle(GetDrivingReportQuery query) {
        return drivingReportViewRepository.findByVehicleIdAndPeriod(
                query.vehicleId(), query.startDate(), query.endDate());
    }
}
