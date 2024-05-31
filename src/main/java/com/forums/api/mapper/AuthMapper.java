package com.forums.api.mapper;

import com.forums.api.dto.response.authentication.LoginResponseDTO;
import com.forums.api.dto.response.authentication.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public LoginResponseDTO userDto_loginResponseDto_mapper(UserDTO userDTO){
            return LoginResponseDTO.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .build();
    }
}
