package com.guardiants.platform.iam.application.internal.commandservices;

import com.guardiants.platform.iam.application.commandservices.UserCommandFailure;
import com.guardiants.platform.iam.application.commandservices.UserCommandService;
import com.guardiants.platform.iam.domain.model.aggregates.User;
import com.guardiants.platform.iam.domain.model.commands.UpdateProfileCommand;
import com.guardiants.platform.iam.domain.repositories.UserRepository;
import com.guardiants.platform.shared.application.result.Result;
import com.guardiants.platform.shared.infrastructure.hashing.HashingService;
import org.springframework.stereotype.Service;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;

    public UserCommandServiceImpl(UserRepository userRepository,
                                  HashingService hashingService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    @Override
    public Result<User, UserCommandFailure> handle(UpdateProfileCommand command) {
        return userRepository.findById(command.userId())
                .map(user -> {
                    user.updateProfile(command.name(), command.email());
                    return Result.<User, UserCommandFailure>success(
                            userRepository.save(user));
                })
                .orElse(Result.failure(new UserCommandFailure.PasswordMismatch()));
    }
}
