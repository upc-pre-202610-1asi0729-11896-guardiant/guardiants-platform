package com.guardiants.platform.alerting.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.alerting.domain.model.aggregates.AlertRule;
import com.guardiants.platform.alerting.domain.model.valueobjects.AlertRuleType;
import com.guardiants.platform.alerting.domain.model.valueobjects.Geofence;
import com.guardiants.platform.alerting.infrastructure.persistence.jpa.entities.AlertRulePersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class AlertRuleEntityAssembler {

    public AlertRulePersistenceEntity toPersistenceEntityFromDomain(AlertRule rule) {
        var entity = new AlertRulePersistenceEntity();
        entity.setId(rule.getId());
        entity.setOwnerId(rule.getOwnerId());
        entity.setVehicleId(rule.getVehicleId());
        entity.setType(rule.getType().name());
        if (rule.getGeofence() != null) {
            entity.setCenterLat(rule.getGeofence().centerLat());
            entity.setCenterLng(rule.getGeofence().centerLng());
            entity.setRadiusMeters(rule.getGeofence().radiusMeters());
        }
        entity.setSpeedThresholdKmh(rule.getSpeedThresholdKmh());
        entity.setProlongedStopThresholdMinutes(rule.getProlongedStopThresholdMinutes());
        entity.setEnabled(rule.isEnabled());
        return entity;
    }

    public AlertRule toDomainFromPersistenceEntity(AlertRulePersistenceEntity entity) {
        Geofence geofence = entity.getCenterLat() != null
                ? new Geofence(entity.getCenterLat(), entity.getCenterLng(), entity.getRadiusMeters())
                : null;
        var rule = new AlertRule(
                entity.getOwnerId(),
                entity.getVehicleId(),
                AlertRuleType.valueOf(entity.getType()),
                geofence,
                entity.getSpeedThresholdKmh(),
                entity.getProlongedStopThresholdMinutes(),
                entity.isEnabled());
        rule.setId(entity.getId());
        return rule;
    }
}
