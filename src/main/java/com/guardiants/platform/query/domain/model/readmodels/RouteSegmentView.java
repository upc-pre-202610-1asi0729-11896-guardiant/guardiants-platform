package com.guardiants.platform.query.domain.model.readmodels;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RouteSegmentView {
    private Long segmentId;
    private Instant startedAt;
    private Instant endedAt;
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;
    private double distanceKm;
    private int durationMinutes;
}
