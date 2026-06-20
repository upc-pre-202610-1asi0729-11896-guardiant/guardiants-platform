package com.guardiants.platform.fleet.interfaces.rest.transform;

import com.guardiants.platform.fleet.domain.model.aggregates.Fleet;
import com.guardiants.platform.fleet.interfaces.rest.resources.FleetResource;

public class FleetResourceFromEntityAssembler {

    public static FleetResource toResourceFromEntity(Fleet fleet) {
        return new FleetResource(fleet.getId(), fleet.getOwnerId(), fleet.getName(),
                fleet.getOrganizationType().name(), null);
    }
}
