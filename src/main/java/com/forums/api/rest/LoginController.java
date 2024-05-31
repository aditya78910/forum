package com.forums.api.rest;

import com.forums.api.dto.response.authentication.LoginResponseDTO;
import com.forums.api.dto.response.authentication.UserDTO;
import com.forums.api.mapper.AuthMapper;
import com.forums.api.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    AuthenticationService authenticationService;

    AuthMapper authMapper;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(HttpServletResponse response, @AuthenticationPrincipal UserDTO userDTO){
      
        Cookie cookie = new Cookie("session", authenticationService.createToken(userDTO));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge((int)Duration.ofDays(1).toSeconds());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(authMapper.userDto_loginResponseDto_mapper(userDTO));
    }
}
