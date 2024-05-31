package com.forums.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequestDTO {

    @Size(max = 40, message = "Name should not exceed 40 characters")
    private String username;
    private String password;

    @Email(message = "Email should be valid")
    private String email;
}
