package com.guardiants.platform.alerting.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import com.guardiants.platform.alerting.domain.model.valueobjects.AlertSeverity;
import com.guardiants.platform.alerting.domain.model.valueobjects.AlertStatus;
import com.guardiants.platform.alerting.domain.model.valueobjects.AlertType;
import com.guardiants.platform.alerting.domain.model.valueobjects.GeoPoint;
import com.guardiants.platform.alerting.infrastructure.persistence.jpa.entities.SecurityAlertPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class SecurityAlertEntityAssembler {

    public SecurityAlertPersistenceEntity toPersistenceEntityFromDomain(SecurityAlert alert) {
        var entity = new SecurityAlertPersistenceEntity();
        entity.setId(alert.getId());
        entity.setOwnerId(alert.getOwnerId());
        entity.setVehicleId(alert.getVehicleId());
        entity.setRuleId(alert.getRuleId());
        entity.setType(alert.getType().name());
        entity.setSeverity(alert.getSeverity().name());
        if (alert.getLocation() != null) {
            entity.setLat(alert.getLocation().lat());
            entity.setLng(alert.getLocation().lng());
        }
        entity.setDescription(alert.getDescription());
        entity.setGeneratedAt(alert.getGeneratedAt());
        entity.setStatus(alert.getStatus().name());
        entity.setAcknowledgedAt(alert.getAcknowledgedAt());
        entity.setClosedAt(alert.getClosedAt());
        return entity;
    }

    public SecurityAlert toDomainFromPersistenceEntity(SecurityAlertPersistenceEntity entity) {
        var alert = new SecurityAlert(
                entity.getOwnerId(),
                entity.getVehicleId(),
                entity.getRuleId(),
                AlertType.valueOf(entity.getType()),
                AlertSeverity.valueOf(entity.getSeverity()),
                new GeoPoint(entity.getLat(), entity.getLng()),
                entity.getDescription(),
                entity.getGeneratedAt(),
                AlertStatus.valueOf(entity.getStatus()),
                entity.getAcknowledgedAt(),
                entity.getClosedAt());
        alert.setId(entity.getId());
        return alert;
    }
}
