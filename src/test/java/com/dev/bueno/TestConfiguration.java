package com.dev.bueno;

import com.dev.bueno.repository.MoradorRepository;
import com.dev.bueno.repository.UsuarioRepository;
import com.dev.bueno.security.JwtService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "com.dev.bueno"
})
public class TestConfiguration {

    @Bean
    public JwtService jwtService() {
        return Mockito.mock(JwtService.class);
    }

    @Bean
    public UsuarioRepository UsuarioRepository() {
        return  Mockito.mock(UsuarioRepository.class);
    }

    @Bean
    public MoradorRepository moradorRepository() {
        return  Mockito.mock(MoradorRepository.class);
    }
}
