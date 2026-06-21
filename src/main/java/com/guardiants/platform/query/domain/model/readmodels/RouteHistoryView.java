package com.guardiants.platform.query.domain.model.readmodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RouteHistoryView {
    private Long id;
    private Long vehicleId;
    private Instant periodStart;
    private Instant periodEnd;
    private List<RouteSegmentView> segments = new ArrayList<>();
    private double totalDistanceKm;
    private int totalDurationMinutes;
    private int totalTripsCount;

    public RouteHistoryView(Long vehicleId, Instant periodStart, Instant periodEnd) {
        this.vehicleId = vehicleId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public boolean hasData() { return !segments.isEmpty(); }

    public void addSegment(RouteSegmentView segment) {
        this.segments.add(segment);
        this.totalTripsCount = segments.size();
        this.totalDistanceKm += segment.getDistanceKm();
        this.totalDurationMinutes += segment.getDurationMinutes();
    }
}
