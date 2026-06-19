package com.guardiants.platform.iam.application.internal.commandservices;

import com.guardiants.platform.iam.application.commandservices.UserCommandFailure;
import com.guardiants.platform.iam.application.commandservices.UserCommandService;
import com.guardiants.platform.iam.domain.model.aggregates.User;
import com.guardiants.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.guardiants.platform.iam.domain.model.commands.UpdatePreferencesCommand;
import com.guardiants.platform.iam.domain.model.commands.UpdateProfileCommand;
import com.guardiants.platform.iam.domain.repositories.AccountRepository;
import com.guardiants.platform.iam.domain.repositories.UserRepository;
import com.guardiants.platform.shared.application.result.Result;
import com.guardiants.platform.shared.infrastructure.hashing.HashingService;
import org.springframework.stereotype.Service;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final AccountRepository accountRepository;

    public UserCommandServiceImpl(UserRepository userRepository,
                                  HashingService hashingService,
                                  AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.accountRepository = accountRepository;
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

    @Override
    public Result<User, UserCommandFailure> handle(ChangePasswordCommand command) {
        var userOpt = userRepository.findById(command.userId());
        if (userOpt.isEmpty()) return Result.failure(new UserCommandFailure.PasswordMismatch());

        // verify current password via Account
        var accountOpt = accountRepository.findByEmail(userOpt.get().getEmail());
        if (accountOpt.isEmpty()) return Result.failure(new UserCommandFailure.PasswordMismatch());

        if (!hashingService.matches(command.currentRawPassword(),
                accountOpt.get().getPasswordHash())) {
            return Result.failure(new UserCommandFailure.PasswordMismatch());
        }
        if (command.newRawPassword().length() < 8) {
            return Result.failure(new UserCommandFailure.WeakPassword());
        }

        var account = accountOpt.get();
        // update password hash on Account — extend Account with setPasswordHash
        account.updatePasswordHash(hashingService.encode(command.newRawPassword()));
        accountRepository.save(account);

        return Result.success(userOpt.get());
    }

    @Override
    public Result<User, UserCommandFailure> handle(UpdatePreferencesCommand command) {
        return userRepository.findById(command.userId())
                .map(user -> {
                    user.updatePreferences(command.language(), command.theme());
                    return Result.<User, UserCommandFailure>success(
                            userRepository.save(user));
                })
                .orElse(Result.failure(new UserCommandFailure.PasswordMismatch()));
    }

}
