// iam/interfaces/acl/IamContextFacade.java
package com.guardiants.platform.iam.interfaces.acl;

import java.util.Optional;

/**
 * ACL facade that exposes IAM capabilities to other bounded contexts.
 * Other BCs depend on this interface — never on IAM internals.
 */
public interface IamContextFacade {
    Optional<Long> fetchUserIdByEmail(String email);
    Optional<String> fetchEmailByUserId(Long userId);
    Optional<String> fetchProfileTypeByUserId(Long userId);
    boolean existsUserById(Long userId);
}