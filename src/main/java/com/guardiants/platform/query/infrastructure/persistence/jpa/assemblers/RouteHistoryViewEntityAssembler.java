package com.guardiants.platform.query.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.query.domain.model.readmodels.RouteHistoryView;
import com.guardiants.platform.query.domain.model.readmodels.RouteSegmentView;
import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.RouteHistoryViewPersistenceEntity;
import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.RouteSegmentViewPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RouteHistoryViewEntityAssembler {

    public RouteHistoryViewPersistenceEntity toPersistenceEntityFromView(RouteHistoryView view) {
        var entity = new RouteHistoryViewPersistenceEntity();
        entity.setId(view.getId());
        entity.setVehicleId(view.getVehicleId());
        entity.setPeriodStart(view.getPeriodStart());
        entity.setPeriodEnd(view.getPeriodEnd());
        entity.setTotalDistanceKm(view.getTotalDistanceKm());
        entity.setTotalDurationMinutes(view.getTotalDurationMinutes());
        entity.setTotalTripsCount(view.getTotalTripsCount());
        var segments = new ArrayList<RouteSegmentViewPersistenceEntity>();
        for (var s : view.getSegments()) {
            var se = new RouteSegmentViewPersistenceEntity();
            se.setSegmentId(s.getSegmentId());
            se.setStartedAt(s.getStartedAt());
            se.setEndedAt(s.getEndedAt());
            se.setStartLat(s.getStartLat());
            se.setStartLng(s.getStartLng());
            se.setEndLat(s.getEndLat());
            se.setEndLng(s.getEndLng());
            se.setDistanceKm(s.getDistanceKm());
            se.setDurationMinutes(s.getDurationMinutes());
            segments.add(se);
        }
        entity.setSegments(segments);
        return entity;
    }

    public RouteHistoryView toViewFromPersistenceEntity(RouteHistoryViewPersistenceEntity entity) {
        var view = new RouteHistoryView();
        view.setId(entity.getId());
        view.setVehicleId(entity.getVehicleId());
        view.setPeriodStart(entity.getPeriodStart());
        view.setPeriodEnd(entity.getPeriodEnd());
        view.setTotalDistanceKm(entity.getTotalDistanceKm());
        view.setTotalDurationMinutes(entity.getTotalDurationMinutes());
        view.setTotalTripsCount(entity.getTotalTripsCount());
        var segments = new ArrayList<RouteSegmentView>();
        for (var se : entity.getSegments()) {
            var s = new RouteSegmentView();
            s.setSegmentId(se.getSegmentId());
            s.setStartedAt(se.getStartedAt());
            s.setEndedAt(se.getEndedAt());
            s.setStartLat(se.getStartLat());
            s.setStartLng(se.getStartLng());
            s.setEndLat(se.getEndLat());
            s.setEndLng(se.getEndLng());
            s.setDistanceKm(se.getDistanceKm());
            s.setDurationMinutes(se.getDurationMinutes());
            segments.add(s);
        }
        view.setSegments(segments);
        return view;
    }
}
