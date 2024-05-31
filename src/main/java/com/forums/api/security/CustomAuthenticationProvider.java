package com.forums.api.security;

import com.forums.api.dto.response.authentication.UserDTO;
import com.forums.api.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Slf4j
@Component
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    AuthenticationService authenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("trying to authenticate inside CustomAuthenticationProvider");
        if(authentication instanceof UsernamePasswordAuthenticationToken){
            log.info("instance is UsernamePasswordAuthenticationToken");
            UserDTO userDTO = authenticationService
                    .authenticateUser(authentication.getName(), (String) authentication.getCredentials());
            return new UsernamePasswordAuthenticationToken(userDTO, null, Collections.emptyList());
        }else if (authentication instanceof PreAuthenticatedAuthenticationToken){
            log.info("instance is PreAuthenticatedAuthenticationToken");
            UserDTO byToken = authenticationService.findByToken(authentication.getName());
            return new UsernamePasswordAuthenticationToken(byToken, null, Collections.emptyList());
        }else{
            throw new RuntimeException("Invalid authentication");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
