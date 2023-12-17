package com.bci.auth.controllers;

import com.bci.auth.commons.dtos.requests.user.UserCreationRequestDto;
import com.bci.auth.commons.dtos.requests.user.UserLoginRequestDto;
import com.bci.auth.commons.dtos.responses.user.UserResponseDto;
import com.bci.auth.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid UserCreationRequestDto userCreationRequestDto) {
        var userResponseDto = userService.createUser(userCreationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PostMapping(value= "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid UserLoginRequestDto loginRequest) {

        var userResponseDto = userService.findUser(loginRequest);
        return ResponseEntity.ok(userResponseDto);
    }


}
