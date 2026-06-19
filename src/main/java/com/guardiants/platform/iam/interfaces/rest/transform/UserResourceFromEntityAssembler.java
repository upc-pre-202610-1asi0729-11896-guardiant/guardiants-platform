package com.guardiants.platform.iam.interfaces.rest.transform;

import com.guardiants.platform.iam.domain.model.aggregates.User;
import com.guardiants.platform.iam.interfaces.rest.resources.UserPreferencesResource;
import com.guardiants.platform.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {

    public static UserResource toResourceFromEntity(User user) {
        var prefs = new UserPreferencesResource(
                user.getPreferences().getLanguage().name(),
                user.getPreferences().getTheme().name());
        return new UserResource(
                user.getId(),
                user.getAccountId(),
                user.getName(),
                user.getEmail(),
                user.getProfileType().name(),
                prefs,
                null,
                null);
    }
}
