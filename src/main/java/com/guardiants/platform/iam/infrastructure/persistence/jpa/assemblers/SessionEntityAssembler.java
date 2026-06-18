package com.guardiants.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.iam.domain.model.aggregates.Session;
import com.guardiants.platform.iam.domain.model.commands.LoginCommand;
import com.guardiants.platform.iam.domain.model.valueobjects.SessionStatus;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.entities.SessionPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class SessionEntityAssembler {

    public SessionPersistenceEntity toPersistenceEntityFromDomain(Session session) {
        var entity = new SessionPersistenceEntity();
        entity.setId(session.getId());
        entity.setUserId(session.getUserId());
        entity.setAccessToken(session.getAccessToken());
        entity.setRefreshToken(session.getRefreshToken());
        entity.setIssuedAt(session.getIssuedAt());
        entity.setExpiresAt(session.getExpiresAt());
        entity.setStatus(session.getStatus().name());
        return entity;
    }

    public Session toDomainFromPersistenceEntity(SessionPersistenceEntity entity) {
        var session = new Session(
                new LoginCommand("", ""),   // placeholder — tokens set below
                entity.getAccessToken(),
                entity.getRefreshToken());
        session.setId(entity.getId());
        session.setUserId(entity.getUserId());
        return session;
    }
}