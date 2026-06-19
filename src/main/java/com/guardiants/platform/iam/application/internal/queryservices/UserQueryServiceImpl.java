package com.guardiants.platform.iam.application.internal.queryservices;

import com.guardiants.platform.iam.application.queryservices.UserQueryService;
import com.guardiants.platform.iam.domain.model.aggregates.User;
import com.guardiants.platform.iam.domain.model.queries.*;
import com.guardiants.platform.iam.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByEmail(query.email());
    }

    @Override
    public boolean handle(ExistsUserByIdQuery query) {
        return userRepository.existsById(query.userId());
    }
}