package com.guardiants.platform.iam.infrastructure.email;

import com.guardiants.platform.iam.application.internal.outboundservices.email.EmailVerificationPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * SMTP email verification adapter.
 * In development logs the verification link; in production sends via JavaMailSender.
 */
@Slf4j
@Component
public class SmtpEmailVerificationAdapter implements EmailVerificationPort {

    @Override
    public void sendVerificationEmail(String email, Long accountId, String token) {
        // TODO: configure JavaMailSender for production
        log.info("EMAIL VERIFICATION — to: {} | accountId: {} | token: {} | link: /api/v1/iam/auth/verify-email?accountId={}&token={}",
                email, accountId, token, accountId, token);
    }
}