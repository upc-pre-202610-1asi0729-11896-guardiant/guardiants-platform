package com.guardiants.platform.commands.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.commands.domain.model.aggregates.LocationShareLink;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.entities.LocationShareLinkPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class LocationShareLinkEntityAssembler {

    public LocationShareLinkPersistenceEntity toPersistenceEntityFromDomain(LocationShareLink link) {
        var entity = new LocationShareLinkPersistenceEntity();
        entity.setId(link.getId());
        entity.setVehicleId(link.getVehicleId());
        entity.setCreatedByUserId(link.getCreatedByUserId());
        entity.setToken(link.getToken());
        entity.setLinkCreatedAt(link.getCreatedAt());
        entity.setExpiresAt(link.getExpiresAt());
        return entity;
    }

    public LocationShareLink toDomainFromPersistenceEntity(LocationShareLinkPersistenceEntity entity) {
        var link = new LocationShareLink(
                entity.getVehicleId(), entity.getCreatedByUserId(), entity.getToken(),
                entity.getLinkCreatedAt(), entity.getExpiresAt());
        link.setId(entity.getId());
        return link;
    }
}
