package com.bci.auth.services;

import com.bci.auth.models.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class JwtTokenServiceTest {

    private final String secretKey = "hwqjbnqwbnrbqwjjqpwr172867417u4mc8m149c81249m48291084m0912840124";
    private final long expirationTime = 60000L;

    @InjectMocks
    private JwtTokenService jwtTokenService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(jwtTokenService, "SECRET_KEY", secretKey);
        ReflectionTestUtils.setField(jwtTokenService, "EXPIRATION_TIME", expirationTime);
    }

    @Test
    public void generateToken_ReturnsNonNullToken_ForValidUser() {
        User user = new User();
        user.setEmail("test@example.com");

        String token = jwtTokenService.generateToken(user);

        assertNotNull("Generated token should not be null", token);
    }

    @Test
    public void validateToken_ReturnsTrue_ForValidToken() {
        User user = new User();
        user.setEmail("test@example.com");
        String token = jwtTokenService.generateToken(user);
        assertTrue("Token should be valid", jwtTokenService.validateToken(token));
    }

    @Test
    public void validateToken_ReturnsFalse_ForInvalidToken() {
        String invalidToken = "invalid.token.here";
        assertFalse("Token should be invalid", jwtTokenService.validateToken(invalidToken));
    }

    @Test
    public void extractUsername_ReturnsCorrectUsername_ForValidToken() {
        User user = new User();
        user.setEmail("test@example.com");
        String token = jwtTokenService.generateToken(user);
        String username = jwtTokenService.extractUsername(token);
        assertEquals( "test@example.com", username, "Extracted username should match");
    }
}
