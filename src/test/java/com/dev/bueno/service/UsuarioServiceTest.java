package com.dev.bueno.service;

import com.dev.bueno.dto.AuthDto;
import com.dev.bueno.dto.LoginDto;
import com.dev.bueno.dto.UsuarioDto;
import com.dev.bueno.entity.Usuario;
import com.dev.bueno.exceptions.SenhaOuLoginInvalidoException;
import com.dev.bueno.repository.UsuarioRepository;
import com.dev.bueno.security.JwtService;
import com.dev.bueno.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@Profile("test")
public class UsuarioServiceTest  {

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private JwtService jwtService;

    private  UsuarioService usuarioService;


    @BeforeEach
    public void setUp() {
        this.usuarioService = new UsuarioServiceImpl(
                usuarioRepository,
                passwordEncoder,
                jwtService,
                modelMapper
        );
    }

    @Test
    @DisplayName("deve ser capaz de salvar um novo usuario")
    public void deveSerCapazDeSalvarUmNovoUsuario() {
        Usuario admin =  Usuario.builder().codigo(10l).nome("Administrador").admin(true)
                .email("admin@gamil.com").senha("1234").build();

        UsuarioDto adminDto =  UsuarioDto.builder().nome("Administrador").admin(true)
                .email("admin@gamil.com").senha("1234").build();

        Mockito.when(usuarioRepository.save(admin)).thenReturn(admin);
        Mockito.when(modelMapper.map(admin, UsuarioDto.class)).thenReturn(adminDto);
        Mockito.when(modelMapper.map(adminDto, Usuario.class)).thenReturn(admin);

        UsuarioDto novoUsuario = usuarioService.salvar(adminDto);

        assertEquals(novoUsuario.getEmail(), admin.getEmail());
        assertEquals(novoUsuario.getNome(), admin.getNome());
    }

    @Test
    @DisplayName("deve ser capaz de fazer login com sucesso")
    public void deveSerCapazDeFazerLogin() {
        String toke = "CF1E12";
        LoginDto adminUser = LoginDto.builder().email("admin@gmail.com").senha("admin").build();
        Usuario usuario = Usuario.builder().nome("Admin").email("admin@gmail.com").admin(true).build();

        Mockito.when(usuarioRepository.findByEmail(adminUser.getEmail())).thenReturn(
                Optional.of(usuario)
        );

        Mockito.when(jwtService.gerarToken(usuario)).thenReturn(toke);
        Mockito.when(passwordEncoder.matches(adminUser.getSenha(), usuario.getSenha())).thenReturn(true);

        AuthDto authDto = usuarioService.login(adminUser);

        assertEquals(authDto.getToken(), toke);
        assertEquals(authDto.getEmail(), usuario.getEmail());
    }

    @Test
    @DisplayName("deve ser capaz de lan??ar uma exce????o quando o usuario n??o for valido")
    public void deveTentarFazerLoginComUsuarioInvalido() {
        LoginDto adminUser = LoginDto.builder().email("admin@gmail.com").senha("admin").build();
        Usuario usuario = Usuario.builder().nome("Admin").email("admin@gmail.com").admin(true).build();

        Mockito.when(usuarioRepository.findByEmail(adminUser.getEmail())).thenReturn(
                Optional.of(usuario)
        );

        Mockito.when(passwordEncoder.matches(adminUser.getSenha(), "8899")).thenReturn(true);

        assertThrows(SenhaOuLoginInvalidoException.class,()-> usuarioService.login(adminUser));
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByEmail(adminUser.getEmail());
        Mockito.verify(jwtService, Mockito.never()).gerarToken(usuario);
    }
}
