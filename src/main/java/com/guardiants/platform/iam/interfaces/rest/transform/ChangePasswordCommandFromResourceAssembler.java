package com.guardiants.platform.iam.interfaces.rest.transform;

import com.guardiants.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.guardiants.platform.iam.interfaces.rest.resources.PasswordChangeResource;

public class ChangePasswordCommandFromResourceAssembler {

    public static ChangePasswordCommand toCommandFromResource(Long userId, PasswordChangeResource resource) {
        return new ChangePasswordCommand(userId, resource.currentPassword(), resource.newPassword());
    }
}
