// iam/application/acl/IamContextFacadeImpl.java
package com.guardiants.platform.iam.application.acl;

import com.guardiants.platform.iam.application.queryservices.UserQueryService;
import com.guardiants.platform.iam.domain.model.queries.*;
import com.guardiants.platform.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class IamContextFacadeImpl implements IamContextFacade {

    private final UserQueryService userQueryService;

    public IamContextFacadeImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public Optional<Long> fetchUserIdByEmail(String email) {
        return userQueryService.handle(new GetUserByEmailQuery(email))
                .map(user -> user.getId());
    }

    @Override
    public Optional<String> fetchEmailByUserId(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId))
                .map(user -> user.getEmail());
    }

    @Override
    public Optional<String> fetchProfileTypeByUserId(Long userId) {
        return userQueryService.handle(new GetUserByIdQuery(userId))
                .map(user -> user.getProfileType().name());
    }

    @Override
    public boolean existsUserById(Long userId) {
        return userQueryService.handle(new ExistsUserByIdQuery(userId));
    }
}