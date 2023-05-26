package com.earthwatch.metadata.security.authtokens;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public class AuthTokenAuthentication implements Authentication {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Integer getPrincipal() {
        return customerId;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }

    public AuthTokenAuthentication(UUID token) {
        this.token = token;
    }
    public AuthTokenAuthentication(UUID token, Integer customerId) {
        this.token = token;
        this.customerId = customerId;
    }

    private UUID token;
    private Integer customerId;
    private Boolean isAuthenticated;
}
