package com.forums.api.dto.response.authentication;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private String username;
    private String email;
    private List<String> roles;
}
