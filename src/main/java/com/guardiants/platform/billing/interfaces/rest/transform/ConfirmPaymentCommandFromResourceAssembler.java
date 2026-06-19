package com.guardiants.platform.billing.interfaces.rest.transform;

import com.guardiants.platform.billing.domain.model.commands.ConfirmPaymentCommand;
import com.guardiants.platform.billing.interfaces.rest.resources.ConfirmPaymentResource;

public class ConfirmPaymentCommandFromResourceAssembler {

    public static ConfirmPaymentCommand toCommandFromResource(ConfirmPaymentResource resource) {
        return new ConfirmPaymentCommand(resource.subscriptionId(), resource.stripePaymentIntentId());
    }
}
