package com.guardiants.platform.shared.infrastructure.security;

import com.guardiants.platform.shared.infrastructure.tokens.jwt.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security configuration for the GOD's Tracker Platform.
 *
 * <p>Stateless JWT-based security. All /api/v1/** endpoints require authentication
 * except authentication endpoints (sign-in, register) and Swagger UI.</p>
 */
@Configuration
public class SecurityConfiguration {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(TokenService tokenService,
                                 UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public BearerTokenAuthenticationTokenFilter bearerTokenAuthenticationTokenFilter() {
        return new BearerTokenAuthenticationTokenFilter(tokenService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/iam/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(bearerTokenAuthenticationTokenFilter(),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}