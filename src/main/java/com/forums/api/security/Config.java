package com.forums.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Config {

    public static String[] WHITE_LIST = {
            "/user/**",
            "/login",
            "/error",
    };

    @Autowired
    CookieAuthenticationFilter cookieAuthenticationFilter;

    @Autowired
    TokenAuthenticationFilter tokenAuthenticationFilter;

    @Autowired
    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .addFilterBefore(tokenAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(usernamePasswordAuthenticationFilter, TokenAuthenticationFilter.class)
                .addFilterBefore(cookieAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(c ->
                        c.requestMatchers(WHITE_LIST).permitAll()

                         .anyRequest().authenticated())
                        .exceptionHandling(c-> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(s-> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .build();

    }
}
