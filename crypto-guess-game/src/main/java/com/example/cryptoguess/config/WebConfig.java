package com.example.cryptoguess.config;


import com.example.cryptoguess.model.GameSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class WebConfig {
    @Bean
    @SessionScope
    public GameSession gameSession() {
        return new GameSession();
    }
}
