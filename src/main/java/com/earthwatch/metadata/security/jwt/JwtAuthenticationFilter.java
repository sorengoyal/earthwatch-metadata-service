package com.earthwatch.metadata.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends AuthenticationFilter {
    public JwtAuthenticationFilter(JwtService jwtService) {
        super(new JwtAuthenticationManager(jwtService), new JwtAuthenticationConverter());
        setSuccessHandler(((request, response, authentication) -> {SecurityContextHolder.getContext().setAuthentication(authentication);}));
    }

    static class JwtAuthenticationConverter implements AuthenticationConverter {
        @Override
        public Authentication convert(HttpServletRequest request) {
            if (request.getHeader("Authorization") != null) {
                String token = request.getHeader("Authorization").split(" " )[1];
                return new JwtAuthentication(token);
            }
            return null;
        }


    }

    static class JwtAuthenticationManager implements AuthenticationManager {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String token = (String) authentication.getCredentials();
            if (token != null) {
                DecodedJWT decodeJwt = jwtService.authenticateToken(token);
                authentication.setAuthenticated(decodeJwt != null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return authentication;
            }
            return null;
        }


        public JwtAuthenticationManager(JwtService jwtService) {
            this.jwtService = jwtService;
        }
        private JwtService jwtService;

    }


}
