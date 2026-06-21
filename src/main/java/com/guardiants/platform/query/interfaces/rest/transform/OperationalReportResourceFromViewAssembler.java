package com.guardiants.platform.query.interfaces.rest.transform;

import com.guardiants.platform.query.domain.model.readmodels.OperationalReportView;
import com.guardiants.platform.query.interfaces.rest.resources.OperationalReportResource;
import com.guardiants.platform.query.interfaces.rest.resources.VehicleOperationalSummaryResource;

public class OperationalReportResourceFromViewAssembler {

    public static OperationalReportResource toResourceFromView(OperationalReportView view) {
        var summaries = view.getVehicleSummaries().stream()
                .map(s -> new VehicleOperationalSummaryResource(
                        s.getVehicleId(), s.getPlate(), s.getTotalDistanceKm(),
                        s.getTotalTripsCount(), s.getAlertsCount(), s.getLoansCount(),
                        s.getDrivingScore()))
                .toList();
        return new OperationalReportResource(
                view.getId(), view.getFleetId(),
                view.getPeriodStart(), view.getPeriodEnd(),
                summaries, view.getTotalAlertsCount(),
                view.getTotalLoansCount(), view.getGeneratedAt());
    }
}
