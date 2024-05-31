package com.forums.api.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.forums.api.dto.request.authentication.AuthRequest;
import com.forums.api.exception.AuthenticationFailedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Component
public class UsernamePasswordAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    Validator validator;

    private static final String LOGIN_ENDPOINT = "/login";
    @Autowired
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    CustomAuthenticationManager customAuthenticationManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("invoked UsernamePasswordAuthenticationFilter {} {}", request.getMethod(), request.getServletPath());

        if (LOGIN_ENDPOINT.equals(request.getServletPath()) &&
                Objects.equals(request.getMethod(), HttpMethod.POST.name())) {

            AuthRequest authRequest = objectMapper.readValue(request.getInputStream(), AuthRequest.class);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());


            BindingResult bindingResult = new BeanPropertyBindingResult(authRequest, "authRequest");
            validator.validate(authRequest, bindingResult);

            if (bindingResult.hasErrors()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request Exception");
                return;
            }

            try {
                Authentication authenticated = customAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authenticated);

            }catch (AuthenticationFailedException ex){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
            }
        }
        filterChain.doFilter(request, response);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        log.info("invoked UsernamePasswordAuthenticationFilter {} {}", request.getMethod(), request.getServletPath());
        if (LOGIN_ENDPOINT.equals(request.getServletPath()) &&
                Objects.equals(request.getMethod(), HttpMethod.POST.name())) {
            AuthRequest authRequest = objectMapper.readValue(request.getInputStream(), AuthRequest.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
            //return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        }

        return null;
    }
}
