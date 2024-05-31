package com.forums.api.dto.request.authentication;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;

}
