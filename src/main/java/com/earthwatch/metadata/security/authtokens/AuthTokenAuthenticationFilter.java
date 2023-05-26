package com.earthwatch.metadata.security.authtokens;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.earthwatch.metadata.entities.CustomerEntity;
import com.earthwatch.metadata.security.jwt.JwtAuthentication;
import com.earthwatch.metadata.security.jwt.JwtAuthenticationFilter;
import com.earthwatch.metadata.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class AuthTokenAuthenticationFilter extends AuthenticationFilter {
    public AuthTokenAuthenticationFilter(AuthTokenService authTokenService) {
        super(new AuthTokenAuthenticationManager(authTokenService), new AuthTokenAuthenticationConverter());
        setSuccessHandler(((request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);}));
    }

    static class AuthTokenAuthenticationConverter implements AuthenticationConverter {
        @Override
        public Authentication convert(HttpServletRequest request) {
            if (request.getHeader("x-auth-token") != null) {
                String token = request.getHeader("x-auth-token");
                return new AuthTokenAuthentication(UUID.fromString(token));
            }
            return null;
        }


    }

    static class AuthTokenAuthenticationManager implements AuthenticationManager {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            UUID token = (UUID) authentication.getCredentials();
            if (token != null) {
                try {
                    CustomerEntity customer = authTokenService.getUserIdFromToken(token);
                    Authentication authentication_verfied = new AuthTokenAuthentication(token, customer.getId());
                    authentication_verfied.setAuthenticated(true);
                    return authentication_verfied;
                }
                catch (BadCredentialsException e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }
            return null;
        }


        public AuthTokenAuthenticationManager(AuthTokenService authTokenService) {
            this.authTokenService = authTokenService;
        }
        private AuthTokenService authTokenService;

    }
}
