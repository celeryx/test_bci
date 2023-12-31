package com.bci.auth.commons.converters;

import com.bci.auth.commons.dtos.responses.user.UserResponseDto;
import com.bci.auth.commons.dtos.user.UserPhoneDto;
import com.bci.auth.models.user.User;
import com.bci.auth.models.user.UserPhone;
import com.bci.auth.services.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserModelToUserResponseDto implements Converter<User, UserResponseDto> {

    private final JwtTokenService jwtTokenService;

    @Autowired
    public UserModelToUserResponseDto(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public UserResponseDto convert(User userModel) {

        var userResponseDto = new UserResponseDto();

        userResponseDto.setId(userModel.getId());
        userResponseDto.setActive(userModel.isActive());
        userResponseDto.setCreated(this.formatToDate(userModel.getCreated()));
        userResponseDto.setEmail(userModel.getEmail());
        userResponseDto.setPhones(this.setPhones(userModel.getPhones()));
        userResponseDto.setName(userModel.getName());
        userResponseDto.setLastLogin(this.formatToDate(userModel.getLastLogin()));
        userResponseDto.setToken(this.jwtTokenService.generateToken(userModel));
        userResponseDto.setPassword(userModel.getPassword());

        return userResponseDto;
    }

    private List<UserPhoneDto> setPhones(List<UserPhone> phones) {
        return phones.stream()
                .map(phone -> new UserPhoneDto(phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
                .collect(Collectors.toList());
    }

    private String formatToDate(LocalDateTime ld) {

        try {
            DateTimeFormatter originalDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            DateTimeFormatter desiredDateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm:ss a");
            String formattedDate = ld.format(desiredDateFormat);

            return formattedDate;
        } catch (Exception e) {
            return null;
        }
    }
}
