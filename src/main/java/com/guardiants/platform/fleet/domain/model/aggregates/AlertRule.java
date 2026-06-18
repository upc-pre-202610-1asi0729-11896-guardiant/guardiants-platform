package com.guardiants.platform.fleet.domain.model.aggregates;

import com.guardiants.platform.fleet.domain.model.valueobjects.AlertRuleType;
import com.guardiants.platform.fleet.domain.model.valueobjects.Geofence;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "fleet_alert_rules")
public class AlertRule extends AbstractDomainAggregateRoot<AlertRule> {

    @Column(nullable = false)
    private Long fleetId;

    private Long vehicleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private AlertRuleType type;

    @Embedded
    private Geofence geofence;

    private Double speedThresholdKmh;
    private Integer prolongedStopThresholdMinutes;

    @Column(nullable = false)
    private boolean enabled = true;

    public boolean appliesToVehicle(Long vehicleId) {
        return this.vehicleId == null || this.vehicleId.equals(vehicleId);
    }
}