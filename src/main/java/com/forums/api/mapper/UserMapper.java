package com.forums.api.mapper;

import com.forums.api.dto.request.UserCreateRequestDTO;
import com.forums.api.dto.response.UserCreateResponseDTO;
import com.forums.api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User userCreateRequestDTO_to_user(UserCreateRequestDTO userCreateRequestDTO){
        return User.builder()
                .username(userCreateRequestDTO.getUsername())
                .emailId(userCreateRequestDTO.getEmail())
                .password(userCreateRequestDTO.getPassword())
                .build();
    }

    public UserCreateResponseDTO user_to_userCreateResponseDTO(User user){
        return UserCreateResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmailId())
                .build();
    }
}
