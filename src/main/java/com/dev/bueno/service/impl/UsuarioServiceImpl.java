package com.dev.bueno.service.impl;

import com.dev.bueno.dto.AuthDto;
import com.dev.bueno.dto.LoginDto;
import com.dev.bueno.dto.UsuarioDto;
import com.dev.bueno.entity.Usuario;
import com.dev.bueno.exceptions.NegocioException;
import com.dev.bueno.exceptions.SenhaOuLoginInvalidoException;
import com.dev.bueno.repository.UsuarioRepository;
import com.dev.bueno.security.JwtService;
import com.dev.bueno.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public UsuarioDto salvar(UsuarioDto usuarioDto) {
        Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        validaSeOUsuarioEstaCadastrado(usuario);
        Usuario novoUsuario = usuarioRepository.save(usuario);
        return modelMapper.map(novoUsuario, UsuarioDto.class);
    }

    private void validaSeOUsuarioEstaCadastrado(Usuario usuario) {
        Optional<Usuario> resultado = usuarioRepository
                .findByEmail(usuario.getEmail());
        if (resultado.isPresent()) {
            throw new NegocioException("Usuário já cadastrado!");
        }
    }


    @Override
    public AuthDto login(LoginDto loginDto) {
        if (loginDto  == null) {
            throw new NegocioException("O login não pode ser null");
        }

        Usuario usuario = obterUsuarioPorLogin(loginDto.getEmail());
        validaSenhas(loginDto, usuario);
        String token = jwtService.gerarToken(usuario);

        return AuthDto.builder()
                .idUsuario(usuario.getCodigo())
                .nome(usuario.getNome().toUpperCase())
                .email(usuario.getEmail())
                .token(token).build();
    }

    public Usuario obterUsuarioPorLogin(String email) {
        return usuarioRepository.
                findByEmail(email).
                orElseThrow(() -> new SenhaOuLoginInvalidoException());
    }

    private void validaSenhas(LoginDto loginDto, Usuario usuario) {
        if (usuario.getChaveGmail() != null  && !usuario.getChaveGmail().isEmpty()) {
            throw new NegocioException("Favor fazer login com sua conta do gmail!");
        }
        boolean isSenhaValida = passwordEncoder.matches(loginDto.getSenha(), usuario.getSenha());
        if (!isSenhaValida) {
            throw new SenhaOuLoginInvalidoException();
        }
    }

}
