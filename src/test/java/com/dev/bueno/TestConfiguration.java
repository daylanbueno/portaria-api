package com.dev.bueno;

import com.dev.bueno.security.JwtService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@ComponentScan({
        "com.dev.bueno",
        "com.dev.bueno.config",
        "com.dev.bueno.security",
        "com.dev.bueno.security.JwtService"
})
public class TestConfiguration {

    @Bean
    public JwtService jwtService() {
        return Mockito.mock(JwtService.class);
    }
}
