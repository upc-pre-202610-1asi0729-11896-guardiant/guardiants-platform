package com.guardiants.platform.telemetry.domain.model.aggregates;

import com.guardiants.platform.telemetry.domain.model.commands.IngestTelemetryPointCommand;
import com.guardiants.platform.telemetry.domain.model.valueobjects.*;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "telemetry_points")
public class TelemetryPoint extends AbstractDomainAggregateRoot<TelemetryPoint> {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false, length = 100)
    private String deviceSerial;

    @Column(nullable = false)
    private Instant timestamp;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "lat")),
            @AttributeOverride(name = "lng", column = @Column(name = "lng"))
    })
    private GeoPoint location;

    @Embedded
    private VehicleStatusSnapshot vehicleStatus;

    @Embedded
    private ConnectivityStatus connectivity;

    /** Constructor invoked by IngestTelemetryPointCommand. */
    public TelemetryPoint(IngestTelemetryPointCommand command) {
        this.vehicleId = command.vehicleId();
        this.deviceSerial = command.deviceSerial();
        this.timestamp = command.timestamp();
        this.location = command.location();
        this.vehicleStatus = command.vehicleStatus();
        this.connectivity = new ConnectivityStatus(command.connectivity(), Instant.now());
    }

    /** Reconstruction constructor used by persistence assemblers. */
    public TelemetryPoint(Long vehicleId, String deviceSerial, Instant timestamp,
                          GeoPoint location, VehicleStatusSnapshot vehicleStatus,
                          ConnectivityStatus connectivity) {
        this.vehicleId = vehicleId;
        this.deviceSerial = deviceSerial;
        this.timestamp = timestamp;
        this.location = location;
        this.vehicleStatus = vehicleStatus;
        this.connectivity = connectivity;
    }

    public boolean isLowFuel(double thresholdPercent) {
        return vehicleStatus.fuelLevelPercent() != null
                && vehicleStatus.fuelLevelPercent() < thresholdPercent;
    }

    public boolean isOverheating(double thresholdC) {
        return vehicleStatus.engineTemperatureC() != null
                && vehicleStatus.engineTemperatureC() > thresholdC;
    }

    public boolean isLowBattery(double thresholdPercent) {
        return vehicleStatus.batteryLevelPercent() < thresholdPercent;
    }

    public boolean isStale(int thresholdMinutes) {
        return timestamp.isBefore(Instant.now().minusSeconds(thresholdMinutes * 60L));
    }
}
