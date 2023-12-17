package com.bci.auth.commons.dtos.responses.user;

import com.bci.auth.commons.dtos.user.UserPhoneDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class UserResponseDto {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<UserPhoneDto> phones;
    private String created;
    private String lastLogin;
    private String token;
    private boolean isActive;

    public UserResponseDto() {
    }

    public UserResponseDto(UUID id, String name, String email, String password,
                           List<UserPhoneDto> phones, String created, String lastLogin, String token, boolean isActive) {
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


    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phones=" + phones +
                ", created=" + created +
                ", lastLogin=" + lastLogin +
                ", token='" + token + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
