package com.guardiants.platform.iam.application.internal.commandservices;

import com.guardiants.platform.iam.application.commandservices.AccountCommandFailure;
import com.guardiants.platform.iam.application.commandservices.AccountCommandService;
import com.guardiants.platform.iam.application.internal.outboundservices.email.EmailVerificationPort;
import com.guardiants.platform.iam.application.internal.outboundservices.events.IamEventPublisher;
import com.guardiants.platform.iam.domain.model.aggregates.Account;
import com.guardiants.platform.iam.domain.model.aggregates.User;
import com.guardiants.platform.iam.domain.model.commands.RegisterAccountCommand;
import com.guardiants.platform.iam.domain.model.events.AccountRegisteredEvent;
import com.guardiants.platform.iam.domain.repositories.AccountRepository;
import com.guardiants.platform.iam.domain.repositories.UserRepository;
import com.guardiants.platform.shared.application.result.Result;
import com.guardiants.platform.shared.infrastructure.hashing.HashingService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountCommandServiceImpl implements AccountCommandService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final EmailVerificationPort emailVerificationPort;
    private final IamEventPublisher iamEventPublisher;

    public AccountCommandServiceImpl(AccountRepository accountRepository,
                                     UserRepository userRepository,
                                     HashingService hashingService,
                                     EmailVerificationPort emailVerificationPort,
                                     IamEventPublisher iamEventPublisher) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.emailVerificationPort = emailVerificationPort;
        this.iamEventPublisher = iamEventPublisher;
    }

    @Override
    public Result<Account, AccountCommandFailure> handle(RegisterAccountCommand command) {
        if (accountRepository.existsByEmail(command.email())) {
            return Result.failure(new AccountCommandFailure.EmailAlreadyExists());
        }

        String passwordHash = hashingService.encode(command.rawPassword());
        String verificationToken = UUID.randomUUID().toString();

        var account = new Account(command, passwordHash, verificationToken);
        var savedAccount = accountRepository.save(account);

        // create User profile automatically
        var user = new User(savedAccount);
        var savedUser = userRepository.save(user);

        // send verification email
        emailVerificationPort.sendVerificationEmail(
                savedAccount.getEmail(), savedAccount.getId(), verificationToken);

        // publish cross-BC event (Billing subscribes to provision a free plan)
        iamEventPublisher.publishAccountRegistered(new AccountRegisteredEvent(
                savedAccount.getId(), savedUser.getId(),
                savedAccount.getEmail(), savedAccount.getProfileType()));

        return Result.success(savedAccount);
    }
}