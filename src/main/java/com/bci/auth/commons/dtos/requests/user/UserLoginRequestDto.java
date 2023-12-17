package com.bci.auth.commons.dtos.requests.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserLoginRequestDto {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid and follow the pattern user@example.com")
    String email;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Z])(?=(?:.*\\d){2})(?!.*(\\d)\\1)[A-Za-z\\d]{8,12}$",
            message = "Password should be 8 to 12 characters long and include at least one uppercase letter and two numbers. The numbers can be non-consecutive.")
    String password;

    public UserLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
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
}
