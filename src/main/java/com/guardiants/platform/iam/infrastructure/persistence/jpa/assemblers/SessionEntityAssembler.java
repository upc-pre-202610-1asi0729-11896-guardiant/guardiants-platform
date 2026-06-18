package com.guardiants.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.iam.domain.model.commands.RegisterSessionCommand;
import com.guardiants.platform.iam.domain.model.valueobjects.ProfileType;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.entities.SessionPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class SessionEntityAssembler {

    public SessionPersistenceEntity toPersistenceEntityFromDomain(Session Session) {
        var entity = new SessionPersistenceEntity();
        entity.setId(Session.getId());
        entity.setEmail(Session.getEmail());
        entity.setPasswordHash(Session.getPasswordHash());
        entity.setProfileType(Session.getProfileType().name());
        entity.setEmailVerified(Session.isEmailVerified());
        entity.setVerificationToken(Session.getVerificationToken());
        return entity;
    }

    public Session toDomainFromPersistenceEntity(SessionPersistenceEntity entity) {
        var command = new RegisterSessionCommand(
                entity.getEmail(), "", // raw password not stored
                ProfileType.valueOf(entity.getProfileType()), "");
        var Session = new Session(command, entity.getPasswordHash(),
                entity.getVerificationToken());
        Session.setId(entity.getId());
        if (entity.isEmailVerified()) Session.verifyEmail(entity.getVerificationToken());
        return Session;
    }
}