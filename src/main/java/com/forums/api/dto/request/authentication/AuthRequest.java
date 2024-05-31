package com.forums.api.dto.request.authentication;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {

    @Size(max = 40, message = "Name should not exceed 40 characters")
    private String username;

    private String password;

}
