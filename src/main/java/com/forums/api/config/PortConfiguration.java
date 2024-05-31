package com.forums.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortConfiguration implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Value("${API_LISTENING_PORT}")
    private int port;

    public void customize(ConfigurableServletWebServerFactory factory){
        factory.setPort(port);
    }
}