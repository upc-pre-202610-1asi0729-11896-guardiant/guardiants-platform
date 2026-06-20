package com.guardiants.platform.fleet.interfaces.rest.transform;

import com.guardiants.platform.fleet.domain.model.commands.CreateFleetCommand;
import com.guardiants.platform.fleet.domain.model.valueobjects.OrganizationType;
import com.guardiants.platform.fleet.interfaces.rest.resources.CreateFleetResource;

public class CreateFleetCommandFromResourceAssembler {

    public static CreateFleetCommand toCommandFromResource(CreateFleetResource resource) {
        return new CreateFleetCommand(resource.ownerId(), resource.name(),
                OrganizationType.valueOf(resource.organizationType()));
    }
}
