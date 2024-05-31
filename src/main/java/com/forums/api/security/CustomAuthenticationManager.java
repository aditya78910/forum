package com.forums.api.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    List<AuthenticationProvider> authenticationProviders;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        for(AuthenticationProvider provider: authenticationProviders){
            if(provider.supports(authentication.getClass())){
                return provider.authenticate(authentication);
            }
        }
        return null;
    }
}
