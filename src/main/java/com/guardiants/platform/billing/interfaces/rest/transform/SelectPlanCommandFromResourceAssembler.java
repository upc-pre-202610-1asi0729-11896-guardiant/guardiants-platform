package com.guardiants.platform.billing.interfaces.rest.transform;

import com.guardiants.platform.billing.domain.model.commands.SelectPlanCommand;
import com.guardiants.platform.billing.interfaces.rest.resources.SelectPlanResource;

public class SelectPlanCommandFromResourceAssembler {

    public static SelectPlanCommand toCommandFromResource(SelectPlanResource resource) {
        return new SelectPlanCommand(resource.ownerId(), resource.planId());
    }
}