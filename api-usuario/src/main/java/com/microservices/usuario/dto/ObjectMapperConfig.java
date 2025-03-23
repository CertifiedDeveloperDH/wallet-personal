package com.microservices.usuario.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.usuario.entity.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
