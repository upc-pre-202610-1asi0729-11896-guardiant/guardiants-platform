package com.guardiants.platform.iam.infrastructure.security;

import com.guardiants.platform.iam.interfaces.acl.IamContextFacade;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Loads platform users for Spring Security by their email (the JWT subject).
 *
 * <p>Authentication is stateless and token-based: by the time a request reaches the
 * {@code BearerTokenAuthenticationTokenFilter} the JWT has already been validated, so this
 * service only needs to confirm the account still exists and expose its authorities. The
 * password is intentionally blank — it is never used for bearer authentication.</p>
 */
@Service
public class IamUserDetailsService implements UserDetailsService {

    private final IamContextFacade iamContextFacade;

    public IamUserDetailsService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userId = iamContextFacade.fetchUserIdByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found for email: " + username));
        var authorities = iamContextFacade.fetchProfileTypeByUserId(userId)
                .map(profileType -> List.of(new SimpleGrantedAuthority("ROLE_" + profileType)))
                .orElseGet(() -> List.of(new SimpleGrantedAuthority("ROLE_USER")));
        return new User(username, "", authorities);
    }
}
