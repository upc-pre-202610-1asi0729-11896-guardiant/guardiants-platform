package com.guardiants.platform.iam.interfaces.rest.transform;

import com.guardiants.platform.iam.domain.model.commands.UpdateProfileCommand;
import com.guardiants.platform.iam.interfaces.rest.resources.ProfileUpdateResource;

public class UpdateProfileCommandFromResourceAssembler {

    public static UpdateProfileCommand toCommandFromResource(Long userId, ProfileUpdateResource resource) {
        return new UpdateProfileCommand(userId, resource.name(), resource.email());
    }
}
