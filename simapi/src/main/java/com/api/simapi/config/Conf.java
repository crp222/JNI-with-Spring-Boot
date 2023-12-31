package com.api.simapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Conf {
    
    @Bean
    public LastUser getLastUser() {
        return new LastUser();
    }
}
