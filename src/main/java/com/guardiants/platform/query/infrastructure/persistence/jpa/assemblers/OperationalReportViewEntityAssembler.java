package com.guardiants.platform.query.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.query.domain.model.readmodels.OperationalReportView;
import com.guardiants.platform.query.domain.model.readmodels.VehicleOperationalSummaryView;
import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.OperationalReportViewPersistenceEntity;
import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.VehicleOperationalSummaryViewPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class OperationalReportViewEntityAssembler {

    public OperationalReportViewPersistenceEntity toPersistenceEntityFromView(OperationalReportView view) {
        var entity = new OperationalReportViewPersistenceEntity();
        entity.setId(view.getId());
        entity.setFleetId(view.getFleetId());
        entity.setPeriodStart(view.getPeriodStart());
        entity.setPeriodEnd(view.getPeriodEnd());
        entity.setTotalAlertsCount(view.getTotalAlertsCount());
        entity.setTotalLoansCount(view.getTotalLoansCount());
        entity.setGeneratedAt(view.getGeneratedAt());
        var summaries = new ArrayList<VehicleOperationalSummaryViewPersistenceEntity>();
        for (var s : view.getVehicleSummaries()) {
            var se = new VehicleOperationalSummaryViewPersistenceEntity();
            se.setVehicleId(s.getVehicleId());
            se.setPlate(s.getPlate());
            se.setTotalDistanceKm(s.getTotalDistanceKm());
            se.setTotalTripsCount(s.getTotalTripsCount());
            se.setAlertsCount(s.getAlertsCount());
            se.setLoansCount(s.getLoansCount());
            se.setDrivingScore(s.getDrivingScore());
            summaries.add(se);
        }
        entity.setVehicleSummaries(summaries);
        return entity;
    }

    public OperationalReportView toViewFromPersistenceEntity(OperationalReportViewPersistenceEntity entity) {
        var view = new OperationalReportView();
        view.setId(entity.getId());
        view.setFleetId(entity.getFleetId());
        view.setPeriodStart(entity.getPeriodStart());
        view.setPeriodEnd(entity.getPeriodEnd());
        view.setTotalAlertsCount(entity.getTotalAlertsCount());
        view.setTotalLoansCount(entity.getTotalLoansCount());
        view.setGeneratedAt(entity.getGeneratedAt());
        var summaries = new ArrayList<VehicleOperationalSummaryView>();
        for (var se : entity.getVehicleSummaries()) {
            var s = new VehicleOperationalSummaryView();
            s.setVehicleId(se.getVehicleId());
            s.setPlate(se.getPlate());
            s.setTotalDistanceKm(se.getTotalDistanceKm());
            s.setTotalTripsCount(se.getTotalTripsCount());
            s.setAlertsCount(se.getAlertsCount());
            s.setLoansCount(se.getLoansCount());
            s.setDrivingScore(se.getDrivingScore());
            summaries.add(s);
        }
        view.setVehicleSummaries(summaries);
        return view;
    }
}
