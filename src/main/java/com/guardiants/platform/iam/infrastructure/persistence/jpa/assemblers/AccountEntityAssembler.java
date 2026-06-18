package com.guardiants.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.iam.domain.model.aggregates.Account;
import com.guardiants.platform.iam.domain.model.commands.RegisterAccountCommand;
import com.guardiants.platform.iam.domain.model.valueobjects.ProfileType;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.entities.AccountPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityAssembler {

    public AccountPersistenceEntity toPersistenceEntityFromDomain(Account account) {
        var entity = new AccountPersistenceEntity();
        entity.setId(account.getId());
        entity.setEmail(account.getEmail());
        entity.setPasswordHash(account.getPasswordHash());
        entity.setProfileType(account.getProfileType().name());
        entity.setEmailVerified(account.isEmailVerified());
        entity.setVerificationToken(account.getVerificationToken());
        return entity;
    }

    public Account toDomainFromPersistenceEntity(AccountPersistenceEntity entity) {
        var command = new RegisterAccountCommand(
                entity.getEmail(), "", // raw password not stored
                ProfileType.valueOf(entity.getProfileType()), "");
        var account = new Account(command, entity.getPasswordHash(),
                entity.getVerificationToken());
        account.setId(entity.getId());
        if (entity.isEmailVerified()) account.verifyEmail(entity.getVerificationToken());
        return account;
    }
}