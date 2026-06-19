package com.guardiants.platform.iam.application.queryservices;

import com.guardiants.platform.iam.domain.model.aggregates.User;
import com.guardiants.platform.iam.domain.model.queries.*;
import java.util.Optional;

public interface UserQueryService {
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
    boolean handle(ExistsUserByIdQuery query);
}