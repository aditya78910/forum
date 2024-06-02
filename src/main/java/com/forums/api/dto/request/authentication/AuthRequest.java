package com.forums.api.dto.request.authentication;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {

    private String username;

    private String password;

}
