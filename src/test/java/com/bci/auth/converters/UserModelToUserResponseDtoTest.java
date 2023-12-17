package com.bci.auth.converters;

import com.bci.auth.commons.converters.UserModelToUserResponseDto;
import com.bci.auth.commons.dtos.responses.user.UserResponseDto;
import com.bci.auth.models.user.User;
import com.bci.auth.models.user.UserPhone;
import com.bci.auth.services.JwtTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserModelToUserResponseDtoTest {

    @Mock
    private JwtTokenService jwtTokenService;

    private UserModelToUserResponseDto converter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtTokenService.generateToken(any(User.class))).thenReturn("fakeToken");
        converter = new UserModelToUserResponseDto(jwtTokenService);
    }

    @Test
    public void convert_ShouldTransformUserToUserResponseDto() {
        // Arrange
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setActive(true);
        user.setCreated(LocalDateTime.now());
        user.setEmail("test@example.com");
        user.setLastLogin(LocalDateTime.now());
        user.setName("Test User");
        user.setPassword("password123");
        user.setPhones(Arrays.asList(new UserPhone(123456789, 1, "57")));

        // Act
        UserResponseDto result = converter.convert(user);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("password123", result.getPassword());
        assertEquals("fakeToken", result.getToken());
        assertTrue(result.isActive());
        assertNotNull(result.getCreated());
        assertNotNull(result.getLastLogin());
        assertNotNull(result.getPhones());
        assertFalse(result.getPhones().isEmpty());
        assertEquals(1, result.getPhones().size());
    }

}
