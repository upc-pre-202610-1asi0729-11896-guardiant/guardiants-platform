package com.guardiants.platform.query.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.query.domain.model.readmodels.DrivingReportView;
import com.guardiants.platform.query.domain.model.readmodels.RiskPatternType;
import com.guardiants.platform.query.domain.model.readmodels.RiskPatternView;
import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.DrivingReportViewPersistenceEntity;
import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.RiskPatternViewPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DrivingReportViewEntityAssembler {

    public DrivingReportViewPersistenceEntity toPersistenceEntityFromView(DrivingReportView view) {
        var entity = new DrivingReportViewPersistenceEntity();
        entity.setId(view.getId());
        entity.setVehicleId(view.getVehicleId());
        entity.setPeriodStart(view.getPeriodStart());
        entity.setPeriodEnd(view.getPeriodEnd());
        entity.setTotalDistanceKm(view.getTotalDistanceKm());
        entity.setTotalDurationMinutes(view.getTotalDurationMinutes());
        entity.setAverageSpeedKmh(view.getAverageSpeedKmh());
        entity.setDrivingScore(view.getDrivingScore());
        entity.setHarshBrakingEvents(view.getHarshBrakingEvents());
        entity.setHarshAccelerationEvents(view.getHarshAccelerationEvents());
        var patterns = new ArrayList<RiskPatternViewPersistenceEntity>();
        for (var p : view.getRiskPatterns()) {
            var pe = new RiskPatternViewPersistenceEntity();
            pe.setType(p.getType() != null ? p.getType().name() : null);
            pe.setOccurrences(p.getOccurrences());
            pe.setLastDetectedAt(p.getLastDetectedAt());
            patterns.add(pe);
        }
        entity.setRiskPatterns(patterns);
        return entity;
    }

    public DrivingReportView toViewFromPersistenceEntity(DrivingReportViewPersistenceEntity entity) {
        var view = new DrivingReportView();
        view.setId(entity.getId());
        view.setVehicleId(entity.getVehicleId());
        view.setPeriodStart(entity.getPeriodStart());
        view.setPeriodEnd(entity.getPeriodEnd());
        view.setTotalDistanceKm(entity.getTotalDistanceKm());
        view.setTotalDurationMinutes(entity.getTotalDurationMinutes());
        view.setAverageSpeedKmh(entity.getAverageSpeedKmh());
        view.setDrivingScore(entity.getDrivingScore());
        view.setHarshBrakingEvents(entity.getHarshBrakingEvents());
        view.setHarshAccelerationEvents(entity.getHarshAccelerationEvents());
        var patterns = new ArrayList<RiskPatternView>();
        for (var pe : entity.getRiskPatterns()) {
            var p = new RiskPatternView();
            p.setType(pe.getType() != null ? RiskPatternType.valueOf(pe.getType()) : null);
            p.setOccurrences(pe.getOccurrences());
            p.setLastDetectedAt(pe.getLastDetectedAt());
            patterns.add(p);
        }
        view.setRiskPatterns(patterns);
        return view;
    }
}
