package com.guardiants.platform.commands.domain.model.entities;

import com.guardiants.platform.commands.domain.model.valueobjects.DeviceConnectionStatus;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DeviceHealth {
    private Long vehicleId;
    private String deviceSerial;
    private String model;
    private String imei;
    private DeviceConnectionStatus status;
    private Instant lastConnectionAt;
    private Double batteryLevelPercent;

    public DeviceHealth(Long vehicleId, String deviceSerial, String model, String imei,
                        DeviceConnectionStatus status, Instant lastConnectionAt,
                        Double batteryLevelPercent) {
        this.vehicleId = vehicleId;
        this.deviceSerial = deviceSerial;
        this.model = model;
        this.imei = imei;
        this.status = status;
        this.lastConnectionAt = lastConnectionAt;
        this.batteryLevelPercent = batteryLevelPercent;
    }

    public Long getVehicleId()                { return vehicleId; }
    public String getDeviceSerial()           { return deviceSerial; }
    public String getModel()                  { return model; }
    public String getImei()                   { return imei; }
    public DeviceConnectionStatus getStatus() { return status; }
    public Instant getLastConnectionAt()      { return lastConnectionAt; }
    public Double getBatteryLevelPercent()    { return batteryLevelPercent; }

    public boolean isOperational() { return status == DeviceConnectionStatus.ACTIVE; }

    public boolean isStale(int thresholdMinutes) {
        return lastConnectionAt.isBefore(
                Instant.now().minus(thresholdMinutes, ChronoUnit.MINUTES));
    }
}
