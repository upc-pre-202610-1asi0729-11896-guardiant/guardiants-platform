package com.guardiants.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.iam.domain.model.aggregates.User;
import com.guardiants.platform.iam.domain.model.commands.RegisterUserCommand;
import com.guardiants.platform.iam.domain.model.valueobjects.ProfileType;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.entities.UserPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityAssembler {

    public UserPersistenceEntity toPersistenceEntityFromDomain(User User) {
        var entity = new UserPersistenceEntity();
        entity.setId(User.getId());
        entity.setEmail(User.getEmail());
        entity.setPasswordHash(User.getPasswordHash());
        entity.setProfileType(User.getProfileType().name());
        entity.setEmailVerified(User.isEmailVerified());
        entity.setVerificationToken(User.getVerificationToken());
        return entity;
    }

    public User toDomainFromPersistenceEntity(UserPersistenceEntity entity) {
        var command = new RegisterUserCommand(
                entity.getEmail(), "", // raw password not stored
                ProfileType.valueOf(entity.getProfileType()), "");
        var User = new User(command, entity.getPasswordHash(),
                entity.getVerificationToken());
        User.setId(entity.getId());
        if (entity.isEmailVerified()) User.verifyEmail(entity.getVerificationToken());
        return User;
    }
}