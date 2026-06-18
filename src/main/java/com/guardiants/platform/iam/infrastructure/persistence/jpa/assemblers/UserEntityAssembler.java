package com.guardiants.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.iam.domain.model.aggregates.User;
import com.guardiants.platform.iam.domain.model.commands.RegisterAccountCommand;
import com.guardiants.platform.iam.domain.model.valueobjects.LanguageValue;
import com.guardiants.platform.iam.domain.model.valueobjects.ProfileType;
import com.guardiants.platform.iam.domain.model.valueobjects.ThemeValue;
import com.guardiants.platform.iam.domain.model.valueobjects.UserPreferences;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.entities.UserPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityAssembler {

    public UserPersistenceEntity toPersistenceEntityFromDomain(User user) {
        var entity = new UserPersistenceEntity();
        entity.setId(user.getId());
        entity.setAccountId(user.getAccountId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setProfileType(user.getProfileType().name());
        entity.setLanguage(user.getPreferences().getLanguage().name());
        entity.setTheme(user.getPreferences().getTheme().name());
        return entity;
    }

    public User toDomainFromPersistenceEntity(UserPersistenceEntity entity) {
        // Reconstruct via a placeholder Account to satisfy User(Account) constructor
        var account = new com.guardiants.platform.iam.domain.model.aggregates.Account(
                new RegisterAccountCommand(entity.getEmail(), "",
                        ProfileType.valueOf(entity.getProfileType()), entity.getName()),
                "", null);
        account.setId(entity.getAccountId());
        var user = new User(account);
        user.setId(entity.getId());
        user.updateProfile(entity.getName(), entity.getEmail());
        user.updatePreferences(
                LanguageValue.valueOf(entity.getLanguage()),
                ThemeValue.valueOf(entity.getTheme()));
        return user;
    }
}