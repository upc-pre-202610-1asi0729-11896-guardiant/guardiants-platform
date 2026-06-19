package com.guardiants.platform.iam.interfaces.rest.transform;

import com.guardiants.platform.iam.domain.model.aggregates.Session;
import com.guardiants.platform.iam.interfaces.rest.resources.SessionResource;

public class SessionResourceFromEntityAssembler {

    public static SessionResource toResourceFromEntity(Session session) {
        return new SessionResource(
                session.getId(),
                session.getUserId(),
                session.getAccessToken(),
                session.getRefreshToken(),
                session.getIssuedAt(),
                session.getExpiresAt(),
                session.getStatus().name());
    }
}
