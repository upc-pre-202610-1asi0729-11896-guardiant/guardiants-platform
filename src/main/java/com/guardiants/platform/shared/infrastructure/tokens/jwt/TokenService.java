// TokenService.java — puerto
package com.guardiants.platform.shared.infrastructure.tokens.jwt;

public interface TokenService {
    String generateToken(String username);
    String getUsernameFromToken(String token);
    boolean validateToken(String token);
}