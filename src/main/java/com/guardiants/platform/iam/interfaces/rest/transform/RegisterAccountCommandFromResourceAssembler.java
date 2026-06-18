package com.guardiants.platform.iam.interfaces.rest.transform;

import com.guardiants.platform.iam.domain.model.commands.RegisterAccountCommand;
import com.guardiants.platform.iam.domain.model.valueobjects.ProfileType;
import com.guardiants.platform.iam.interfaces.rest.resources.RegisterAccountResource;

public class RegisterAccountCommandFromResourceAssembler {

    public static RegisterAccountCommand toCommandFromResource(RegisterAccountResource resource) {
        return new RegisterAccountCommand(
                resource.email(),
                resource.password(),
                ProfileType.valueOf(resource.profileType()),
                resource.name());
    }
}