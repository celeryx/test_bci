package com.bci.auth.services;

import com.bci.auth.commons.converters.UserCreationRqToUserModel;
import com.bci.auth.commons.converters.UserModelToUserResponseDto;
import com.bci.auth.commons.dtos.requests.user.UserCreationRequestDto;
import com.bci.auth.commons.dtos.requests.user.UserLoginRequestDto;
import com.bci.auth.commons.dtos.responses.user.UserResponseDto;
import com.bci.auth.commons.exceptions.models.UserNotFoundException;
import com.bci.auth.models.user.UserPhone;
import com.bci.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserCreationRqToUserModel userCreationRqToUserModel;
    private final UserModelToUserResponseDto userModelToUserResponseDto;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserCreationRqToUserModel userCreationRqToUserModel,
                       UserModelToUserResponseDto userModelToUserResponseDto,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtTokenService jwtTokenService) {

        this.userRepository = userRepository;
        this.userCreationRqToUserModel = userCreationRqToUserModel;
        this.userModelToUserResponseDto = userModelToUserResponseDto;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    @Transactional
    public UserResponseDto createUser(UserCreationRequestDto userCreationRequestDto) {
        var user = userCreationRqToUserModel.convert(userCreationRequestDto);
        var savedUser = userRepository.save(user);
        var responseUser = userModelToUserResponseDto.convert(savedUser);
        return responseUser;
    }

    @Transactional
    public UserResponseDto findUser(UserLoginRequestDto loginRequest) {
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Invalid credentials"));
        boolean isPasswordMatch = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (isPasswordMatch) {
            List<UserPhone> phones = user.getPhones();
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            var responseUser = userModelToUserResponseDto.convert(user);
            return responseUser;
        }

        throw new UserNotFoundException("Invalid credentials");
    }
}
