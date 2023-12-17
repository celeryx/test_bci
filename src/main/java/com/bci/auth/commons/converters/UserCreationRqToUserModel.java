package com.bci.auth.commons.converters;

import com.bci.auth.commons.dtos.requests.user.UserCreationRequestDto;
import com.bci.auth.commons.dtos.user.UserPhoneDto;
import com.bci.auth.models.user.User;
import com.bci.auth.models.user.UserPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserCreationRqToUserModel implements Converter<UserCreationRequestDto, User> {

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserCreationRqToUserModel(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User convert(UserCreationRequestDto userRq) {

        var user = new User();

        user.setName(userRq.getName());
        user.setEmail(userRq.getEmail());
        user.setActive(true);
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userRq.getPassword()));
        user.setPhones(this.setPhones(userRq.getPhones()));

        return user;
    }

    private List<UserPhone> setPhones(List<UserPhoneDto> phones) {
        return Objects.isNull(phones) ? Collections.emptyList() :
                phones.stream()
                        .filter(Objects::nonNull)
                        .map(phone -> new UserPhone(phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
                        .collect(Collectors.toList());
    }
}
