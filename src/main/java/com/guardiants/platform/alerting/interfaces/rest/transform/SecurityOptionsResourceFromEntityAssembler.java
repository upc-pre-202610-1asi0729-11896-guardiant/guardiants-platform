package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.domain.model.entities.SecurityOptions;
import com.guardiants.platform.alerting.interfaces.rest.resources.SecurityOptionsResource;

public class SecurityOptionsResourceFromEntityAssembler {

    public static SecurityOptionsResource toResourceFromEntity(SecurityOptions options) {
        return new SecurityOptionsResource(
                options.getId(),
                options.getOwnerId(),
                options.isSuspiciousMovementEnabled(),
                options.isAutoEngineShutdownEnabled(),
                options.isAutoSafeModeEnabled(),
                options.getUpdatedAt());
    }
}
