package com.guardiants.platform.commands.interfaces.rest.transform;

import com.guardiants.platform.commands.domain.model.commands.IssueEngineBlockCommand;
import com.guardiants.platform.commands.interfaces.rest.resources.IssueEngineBlockResource;

public class IssueEngineBlockCommandFromResourceAssembler {

    public static IssueEngineBlockCommand toCommandFromResource(
            IssueEngineBlockResource resource) {
        return new IssueEngineBlockCommand(resource.vehicleId(),
                resource.issuedByUserId(), resource.triggeredByAlertId());
    }
}
