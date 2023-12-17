package com.bci.auth.converters;

import com.bci.auth.commons.converters.UserCreationRqToUserModel;
import com.bci.auth.commons.dtos.requests.user.UserCreationRequestDto;
import com.bci.auth.commons.dtos.user.UserPhoneDto;
import com.bci.auth.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserCreationRqToUserModelTest {

    private UserCreationRqToUserModel converter;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        converter = new UserCreationRqToUserModel(passwordEncoder);
    }

    @Test
    public void convert_ShouldTransformUserCreationRequestDtoToUser() {
        // Arrange
        UserCreationRequestDto userCreationRequestDto = new UserCreationRequestDto();
        userCreationRequestDto.setName("Test User");
        userCreationRequestDto.setEmail("test@example.com");
        userCreationRequestDto.setPassword("password123");
        userCreationRequestDto.setPhones(Arrays.asList(new UserPhoneDto(123456789, 1, "57")));

        // Act
        User result = converter.convert(userCreationRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertTrue(result.isActive());
        assertNotNull(result.getCreated());
        assertNotNull(result.getLastLogin());
        assertNotNull(result.getPhones());
        assertFalse(result.getPhones().isEmpty());
        assertEquals(1, result.getPhones().size());
        assertEquals(123456789, result.getPhones().get(0).getNumber());
        assertEquals(1, result.getPhones().get(0).getCityCode());
        assertEquals("57", result.getPhones().get(0).getCountryCode());
    }
}

