package com.guardiants.platform.fleet.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.fleet.domain.model.aggregates.Fleet;
import com.guardiants.platform.fleet.domain.model.commands.CreateFleetCommand;
import com.guardiants.platform.fleet.domain.model.valueobjects.OrganizationType;
import com.guardiants.platform.fleet.infrastructure.persistence.jpa.entities.FleetPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class FleetEntityAssembler {

    public FleetPersistenceEntity toPersistenceEntityFromDomain(Fleet fleet) {
        var entity = new FleetPersistenceEntity();
        entity.setId(fleet.getId());
        entity.setOwnerId(fleet.getOwnerId());
        entity.setName(fleet.getName());
        entity.setOrganizationType(fleet.getOrganizationType().name());
        return entity;
    }

    public Fleet toDomainFromPersistenceEntity(FleetPersistenceEntity entity) {
        var fleet = new Fleet(new CreateFleetCommand(
                entity.getOwnerId(),
                entity.getName(),
                OrganizationType.valueOf(entity.getOrganizationType())));
        fleet.setId(entity.getId());
        return fleet;
    }
}
