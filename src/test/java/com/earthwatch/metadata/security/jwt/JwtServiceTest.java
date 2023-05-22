package com.earthwatch.metadata.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtServiceTest {
    @Test
    void test_create_whenValidUserID_thenSuccessful() {
        //Arrange

        //Act
        String result = classUnderTest.create(TEST_USER_ID);

        //Assert
        DecodedJWT decodedJWT = JWT.decode(result);
        assertEquals(HSHA_HEADER, new String(base64Decoder.decode(decodedJWT.getHeader())));
        assertEquals("1", decodedJWT.getSubject());
    }

    @Test
    void test_getUserIdFromToken_whenValidUserID_thenSuccessful() {
        //Arrange

        //Act
        Integer result = classUnderTest.getUserIdFromToken(TEST_JWT_TOKEN);

        //Assert
        assertEquals(1, result);

    }

    private Base64.Decoder base64Decoder = Base64.getUrlDecoder();
    private String HSHA_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
    private Integer TEST_USER_ID = 1;
    private String TEST_JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjg0NzExNjIwLCJleHAiOjE2ODUzMTY0MjB9.iLQzsKnhcjAuSVKS9AXGIoCtiDRxDTB3g8Tv5X9BJms";
    private JwtService classUnderTest = new JwtService();
}
