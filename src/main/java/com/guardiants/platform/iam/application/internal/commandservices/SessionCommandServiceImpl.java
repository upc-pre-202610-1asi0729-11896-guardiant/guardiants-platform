package com.guardiants.platform.iam.application.internal.commandservices;

import com.guardiants.platform.iam.application.commandservices.AccountCommandFailure;
import com.guardiants.platform.iam.application.commandservices.SessionCommandFailure;
import com.guardiants.platform.iam.application.commandservices.SessionCommandService;
import com.guardiants.platform.iam.domain.model.aggregates.Session;
import com.guardiants.platform.iam.domain.model.commands.LoginCommand;
import com.guardiants.platform.iam.domain.model.commands.RefreshSessionCommand;
import com.guardiants.platform.iam.domain.repositories.AccountRepository;
import com.guardiants.platform.iam.domain.repositories.SessionRepository;
import com.guardiants.platform.iam.domain.repositories.UserRepository;
import com.guardiants.platform.shared.application.result.Result;
import com.guardiants.platform.shared.infrastructure.hashing.HashingService;
import com.guardiants.platform.shared.infrastructure.tokens.jwt.TokenService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionCommandServiceImpl implements SessionCommandService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public SessionCommandServiceImpl(AccountRepository accountRepository,
                                     UserRepository userRepository,
                                     SessionRepository sessionRepository,
                                     HashingService hashingService,
                                     TokenService tokenService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Override
    public Result<Session, SessionCommandFailure> handle(LoginCommand command) {
        var accountOpt = accountRepository.findByEmail(command.email());
        if (accountOpt.isEmpty()) {
            return Result.failure(new SessionCommandFailure.InvalidCredentials());
        }
        var account = accountOpt.get();
        if (!hashingService.matches(command.rawPassword(), account.getPasswordHash())) {
            return Result.failure(new SessionCommandFailure.InvalidCredentials());
        }
        if (!account.isEmailVerified()) {
            // map to SessionCommandFailure so the response assembler handles it consistently
            return Result.failure(new SessionCommandFailure.InvalidCredentials());
        }

        var user = userRepository.findByAccountId(account.getId());
        if (user.isEmpty()) {
            return Result.failure(new SessionCommandFailure.InvalidCredentials());
        }

        String accessToken = tokenService.generateToken(account.getEmail());
        String refreshToken = UUID.randomUUID().toString();

        var session = new Session(command, accessToken, refreshToken);
        session.setUserId(user.get().getId());
        return Result.success(sessionRepository.save(session));
    }

    @Override
    public Result<Session, SessionCommandFailure> handle(RefreshSessionCommand command) {
        return sessionRepository.findByRefreshToken(command.refreshToken())
                .map(session -> {
                    if (!session.isActive() || session.isExpired()) {
                        return Result.<Session, SessionCommandFailure>failure(
                                new SessionCommandFailure.SessionExpired());
                    }
                    var account = accountRepository.findById(session.getUserId()).orElse(null);
                    if (account == null) {
                        return Result.<Session, SessionCommandFailure>failure(
                                new SessionCommandFailure.InvalidCredentials());
                    }
                    String newAccessToken = tokenService.generateToken(account.getEmail());
                    session.refresh(newAccessToken,
                            java.time.Instant.now().plus(7, java.time.temporal.ChronoUnit.DAYS));
                    return Result.<Session, SessionCommandFailure>success(
                            sessionRepository.save(session));
                })
                .orElse(Result.failure(new SessionCommandFailure.SessionExpired()));
    }
}