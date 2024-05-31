package com.forums.api.rest;

import com.forums.api.dto.request.UserCreateRequestDTO;
import com.forums.api.dto.response.UserCreateResponseDTO;
import com.forums.api.entity.User;
import com.forums.api.mapper.UserMapper;
import com.forums.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserCreateResponseDTO> registerUser(@Valid @RequestBody UserCreateRequestDTO userCreateRequestDTO){
        User savedUser =  userService.createUser(userMapper.userCreateRequestDTO_to_user(userCreateRequestDTO));
        return new ResponseEntity<>(userMapper.user_to_userCreateResponseDTO(savedUser), HttpStatus.CREATED);
    }
}
