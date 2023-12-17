package com.bci.auth.services;

import com.bci.auth.commons.converters.UserCreationRqToUserModel;
import com.bci.auth.commons.converters.UserModelToUserResponseDto;
import com.bci.auth.commons.dtos.requests.user.UserCreationRequestDto;
import com.bci.auth.commons.dtos.requests.user.UserLoginRequestDto;
import com.bci.auth.commons.dtos.responses.user.UserResponseDto;
import com.bci.auth.commons.exceptions.models.UserNotFoundException;
import com.bci.auth.models.user.User;
import com.bci.auth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCreationRqToUserModel userCreationRqToUserModel;

    @Mock
    private UserModelToUserResponseDto userModelToUserResponseDto;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserCreationRequestDto userCreationRequestDto;
    private UserResponseDto userResponseDto;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encoded-password");

        userCreationRequestDto = new UserCreationRequestDto();
        userResponseDto = new UserResponseDto();

        lenient().when(userCreationRqToUserModel.convert(any(UserCreationRequestDto.class))).thenReturn(user);
        lenient().when(userRepository.save(any(User.class))).thenReturn(user);
        lenient().when(userModelToUserResponseDto.convert(any(User.class))).thenReturn(userResponseDto);
    }

    @Test
    public void createUser_ShouldReturnUserResponseDto_WhenUserCreationRequestDtoIsValid() {
        // Act
        UserResponseDto result = userService.createUser(userCreationRequestDto);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void findUser_ShouldReturnUserResponseDto_WhenCredentialsAreValid() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // Act
        UserResponseDto result = userService.findUser(new UserLoginRequestDto(user.getEmail(), "password"));

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void findUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findUser(new UserLoginRequestDto("wrong@example.com", "password"));
        });
    }

    @Test
    public void findUser_ShouldThrowUserNotFoundException_WhenPasswordDoesNotMatch() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> {
            userService.findUser(new UserLoginRequestDto(user.getEmail(), "wrongpassword"));
        });
    }
}

