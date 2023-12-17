package com.bci.auth.commons.dtos.requests.user;

import com.bci.auth.commons.decorators.ListWithNullObjects;
import com.bci.auth.commons.dtos.user.UserPhoneDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserCreationRequestDto {
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid and follow the pattern user@example.com")
    @Size(max = 150, message = "Max. size for email is 150 characters")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Z])(?=(?:.*\\d){2})(?!.*(\\d)\\1)[A-Za-z\\d]{8,12}$",
            message = "Password should be 8 to 12 characters long and include at least one uppercase letter and two numbers. The numbers can be non-consecutive.")
    private String password;


    @ListWithNullObjects(allowNull = true)
    private List<UserPhoneDto> phones;

    public UserCreationRequestDto() {
    }

    public UserCreationRequestDto(String name, String email, String password, List<UserPhoneDto> phones) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones;
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
}
