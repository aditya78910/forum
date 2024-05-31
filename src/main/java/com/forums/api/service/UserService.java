package com.forums.api.service;

import com.forums.api.dto.request.UserCreateRequestDTO;
import com.forums.api.dto.response.UserCreateResponseDTO;
import com.forums.api.entity.User;
import com.forums.api.exception.ErrorResponse;
import com.forums.api.exception.RegisterDuplicateUserException;
import com.forums.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            User savedUser = userRepository.save(user);
            return savedUser;
        }catch (DataIntegrityViolationException ex){
            log.error("Error occurred while registering user {}",user.getUsername(), ex);
            throw new RegisterDuplicateUserException(ErrorResponse.<String>builder()
                    .message("User already registered")
                    .data(user.getUsername())
                    .build());
        }
    }
}
