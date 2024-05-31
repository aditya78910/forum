package com.forums.api.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserCreateRequestDTO {
    private String username;
    private String password;

    @Email(message = "Email should be valid")
    private String email;
}
