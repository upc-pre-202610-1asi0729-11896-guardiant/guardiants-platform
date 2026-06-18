package com.guardiants.platform.iam.interfaces.rest.transform;

import com.guardiants.platform.iam.domain.model.aggregates.Account;
import com.guardiants.platform.iam.interfaces.rest.resources.AccountResource;
import java.time.Instant;

public class AccountResourceFromEntityAssembler {

    public static AccountResource toResourceFromEntity(Account account) {
        return new AccountResource(
                account.getId(),
                account.getEmail(),
                account.getProfileType().name(),
                account.isEmailVerified(),
                Instant.now()); // createdAt from AuditableAbstractPersistenceEntity
    }
}