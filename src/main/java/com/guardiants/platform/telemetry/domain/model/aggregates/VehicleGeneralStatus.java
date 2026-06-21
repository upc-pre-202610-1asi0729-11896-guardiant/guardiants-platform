package com.guardiants.platform.telemetry.domain.model.aggregates;

import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "vehicle_general_statuses")
public class VehicleGeneralStatus extends AbstractDomainAggregateRoot<VehicleGeneralStatus> {

    @Column(nullable = false, unique = true)
    private Long vehicleId;

    private Long latestPointId;

    @Column(nullable = false)
    private int activeAlertCount = 0;

    @Column(nullable = false)
    private boolean isLocked = false;

    @Column(nullable = false)
    private Instant lastUpdatedAt;

    public VehicleGeneralStatus(Long vehicleId) {
        this.vehicleId = vehicleId;
        this.lastUpdatedAt = Instant.now();
    }

    /** Reconstruction constructor used by persistence assemblers. */
    public VehicleGeneralStatus(Long vehicleId, Long latestPointId, int activeAlertCount,
                                boolean isLocked, Instant lastUpdatedAt) {
        this.vehicleId = vehicleId;
        this.latestPointId = latestPointId;
        this.activeAlertCount = activeAlertCount;
        this.isLocked = isLocked;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public void applyTelemetryPoint(TelemetryPoint point) {
        this.latestPointId = point.getId();
        this.lastUpdatedAt = Instant.now();
    }
}
