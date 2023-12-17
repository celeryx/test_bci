package com.bci.auth.commons.dtos.responses.user;

import com.bci.auth.commons.dtos.user.UserPhoneDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserResponseDto {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<UserPhoneDto> phones;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;

    public UserResponseDto() {
    }

    public UserResponseDto(UUID id, String name, String email, String password, List<UserPhoneDto> phones,
                           LocalDateTime created, LocalDateTime lastLogin, String token, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones;
        this.created = created;
        this.lastLogin = lastLogin;
        this.token = token;
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserPhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<UserPhoneDto> phones) {
        this.phones = phones;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
