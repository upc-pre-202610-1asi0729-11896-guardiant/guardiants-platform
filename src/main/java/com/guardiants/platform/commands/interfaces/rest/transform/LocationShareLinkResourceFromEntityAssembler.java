package com.guardiants.platform.commands.interfaces.rest.transform;

import com.guardiants.platform.commands.domain.model.aggregates.LocationShareLink;
import com.guardiants.platform.commands.interfaces.rest.resources.LocationShareLinkResource;

public class LocationShareLinkResourceFromEntityAssembler {

    public static LocationShareLinkResource toResourceFromEntity(LocationShareLink link) {
        return new LocationShareLinkResource(link.getId(), link.getVehicleId(),
                link.getCreatedByUserId(), link.getToken(),
                link.getCreatedAt(), link.getExpiresAt());
    }
}
