package com.earthwatch.metadata.security.authtokens;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.earthwatch.metadata.entities.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthTokenService {
    public UUID createAuthToken(CustomerEntity customerEntity) {
        AuthTokenEntity authTokenEntity = new AuthTokenEntity();
        authTokenEntity.setCustomer(customerEntity);
        AuthTokenEntity savedAuthToken = authTokenRepository.save(authTokenEntity);
        return savedAuthToken.getId();
    }

    public CustomerEntity getUserIdFromToken(UUID authTokenId) {
        Optional<AuthTokenEntity> saveAuthToken = authTokenRepository.findById(authTokenId);
        if (saveAuthToken.isEmpty()) {
            throw new BadCredentialsException("Invalid Auth Token");
        }
        return saveAuthToken.get().getCustomer();
    }

    @Autowired
    private AuthTokenRepository authTokenRepository;
}
