package com.guardiants.platform.commands.interfaces.rest.transform;

import com.guardiants.platform.commands.domain.model.commands.GenerateLocationShareLinkCommand;
import com.guardiants.platform.commands.interfaces.rest.resources.GenerateLocationShareLinkResource;

public class GenerateLocationShareLinkCommandFromResourceAssembler {

    public static GenerateLocationShareLinkCommand toCommandFromResource(
            GenerateLocationShareLinkResource resource) {
        return new GenerateLocationShareLinkCommand(resource.vehicleId(),
                resource.createdByUserId(), resource.expiresAt());
    }
}
