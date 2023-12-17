package com.bci.auth.controllers;


import com.bci.auth.commons.configs.BCryptPasswordEncoderBean;
import com.bci.auth.commons.dtos.requests.user.UserCreationRequestDto;
import com.bci.auth.commons.dtos.requests.user.UserLoginRequestDto;
import com.bci.auth.commons.dtos.responses.user.UserResponseDto;
import com.bci.auth.services.JwtTokenService;
import com.bci.auth.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private JwtTokenService jwtTokenService;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    private BCryptPasswordEncoderBean passwordEncoderBean;

    @Autowired
    private ObjectMapper objectMapper;

    private UserCreationRequestDto userCreationRequestDto;
    private UserLoginRequestDto userLoginRequestDto;
    private UserResponseDto userResponseDto;


    @BeforeEach
    public void setup() {
        userCreationRequestDto = new UserCreationRequestDto("Julio Gonzalez", "julio@t1es2tssw.cl", "a2asfGfdfdf4", new ArrayList<>());
        userLoginRequestDto = new UserLoginRequestDto("julio@t1es2tssw.cl", "a2asfGfdfdf4");
        userResponseDto = new UserResponseDto(UUID.randomUUID(), "Julio Gonzalez", "julio@t1es2tssw.cl",
                "$2a$10$17woQH1R7WVRdwI3ZE8CHu.UgzhLaPW3iszLeDF.Xq3ZHq4/jpscK", new ArrayList<>(), LocalDateTime.now().toString(),
                LocalDateTime.now().toString(),
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWxpb0B0MWVzMnRzc3cuY2wiLCJpYXQiOjE3MDI3ODkyNDIsImV4cCI6MTcwMjc4OTU0Mn0.5ZXcVB2FYqSE2SIC3QDqVSMbvGHDYuGdceD5-293eOFLR5SypjUNGaPoKSBn3BkOPfKLJHt04BTIO0WNX5wdKQ",
                true);
    }

    @Test
    public void signup_ShouldReturnCreatedStatus() throws Exception {
        when(userService.createUser(any(UserCreationRequestDto.class))).thenReturn(userResponseDto);

        String request = objectMapper.writeValueAsString(userCreationRequestDto);

        mockMvc.perform(post("/sign-up")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Julio Gonzalez"))
                .andExpect(jsonPath("$.email").value("julio@t1es2tssw.cl"))
                .andExpect(jsonPath("$.isActive").value(true))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void login_ShouldReturnOkStatus() throws Exception {
        when(userService.findUser(any(UserLoginRequestDto.class))).thenReturn(userResponseDto);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Julio Gonzalez"))
                .andExpect(jsonPath("$.email").value("julio@t1es2tssw.cl"))
                .andExpect(jsonPath("$.isActive").value(true))
                .andDo(print());
    }
}
