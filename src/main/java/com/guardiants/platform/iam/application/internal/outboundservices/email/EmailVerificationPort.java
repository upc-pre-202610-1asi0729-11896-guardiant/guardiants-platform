package com.guardiants.platform.iam.application.internal.outboundservices.email;

public interface EmailVerificationPort {
    void sendVerificationEmail(String email, Long accountId, String token);
}