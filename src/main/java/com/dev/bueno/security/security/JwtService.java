package com.dev.bueno.security.security;

import com.dev.bueno.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String chaveAssinatura;

    @Value("${jwt.experation}")
    private Long expiracao;

    public String gerarToken(Usuario usuario) {
        long expiracaoValue = Long.valueOf(expiracao);
        // pega hora atual e adiciona os minutos.
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracaoValue);

        // pega horaExpiracao com a zona do sistema converte e instante
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(usuario.getEmail())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    public Boolean isTokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExpricacao = claims.getExpiration();
            LocalDateTime data = dataExpricacao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        } catch (Exception ex) {
            return false;
        }
    }


    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public String obterLoginUsuario(String token)  throws  ExpiredJwtException {
        return obterClaims(token).getSubject();
    }
}