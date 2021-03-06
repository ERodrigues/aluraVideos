package com.alura.videos.config.security.service;

import com.alura.videos.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenApiService {

    @Value("${videos.jwt.expiration}")
    private String tempoExpiracaoToken;

    @Value("${videos.jwt.secret}")
    private String segredoToken;

    public String gerarToken(Authentication authenticate) {
        Usuario user = (Usuario) authenticate.getPrincipal();
        Date hoje = new Date();
        Date dataExpira = new Date(hoje.getTime() + Long.parseLong(tempoExpiracaoToken));
        return Jwts.builder()
                .setIssuer("AluraVideos")
                .setSubject(user.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpira)
                .signWith(SignatureAlgorithm.HS256, segredoToken)
                .compact();
    }

    public boolean eValido(String token) {
        try{
            Jwts.parser().setSigningKey(this.segredoToken).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Long getIdUser(String token) {
        return Long.parseLong(Jwts.parser().setSigningKey(segredoToken).parseClaimsJws(token).getBody().getSubject());
    }
}
