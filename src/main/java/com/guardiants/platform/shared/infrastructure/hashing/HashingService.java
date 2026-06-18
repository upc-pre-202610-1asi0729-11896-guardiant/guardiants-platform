// HashingService.java — puerto
package com.guardiants.platform.shared.infrastructure.hashing;

public interface HashingService {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodedPassword);
}