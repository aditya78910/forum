package com.forums.api.security;

import com.forums.api.dto.response.authentication.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Value("${ADMIN_API_KEY}")
    private String apiKey;

    @Autowired
    CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Token");

        if(!ObjectUtils.isEmpty(tokenHeader) && tokenHeader.equals(apiKey)){
            UserDTO userDto = UserDTO.builder()
                    .username("TEST_USER")
                    .email("TEST_EMAIL@gmail.com")
                    .roles(Arrays.asList("admin"))
                    .build();
            UsernamePasswordAuthenticationToken authenticated =
                    new UsernamePasswordAuthenticationToken(userDto, null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authenticated);
        }

        filterChain.doFilter(request, response);
    }
}
