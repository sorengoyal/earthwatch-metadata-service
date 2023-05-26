package com.earthwatch.metadata.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private String SECRET_KEY = "move_key_to_config";
    private Integer EXPIRY_TIME_IN_MS = 7*24*60*60*1000;
    private Algorithm algorithm =  Algorithm.HMAC256(SECRET_KEY);

    public String create(Integer userId) {
        String token = JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRY_TIME_IN_MS))
                .sign(algorithm);
        return token;
    }

    public Integer getUserIdFromToken(String jwt) {
        DecodedJWT decodedJWT = JWT.decode(jwt);
        String subject = decodedJWT.getSubject();
        return Integer.parseInt(subject);
    }

    public DecodedJWT authenticateToken(String jwt) {
        JWTVerifier verifier = JWT.require(algorithm).build();
         DecodedJWT decodedJWT = verifier.verify(jwt);
         return decodedJWT;
    }
}
