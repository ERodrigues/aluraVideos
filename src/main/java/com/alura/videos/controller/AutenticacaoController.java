package com.alura.videos.controller;

import com.alura.videos.config.security.service.TokenApiService;
import com.alura.videos.dto.LoginDto;
import com.alura.videos.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Profile("prod")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager gerenciadorDeAutenticacao;
    @Autowired
    private TokenApiService servicoToken;


    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginDto loginDto){
        UsernamePasswordAuthenticationToken login = loginDto.convert();

        try{
            Authentication autenticacao = gerenciadorDeAutenticacao.authenticate(login);
            String token = servicoToken.gerarToken(autenticacao);
            return ResponseEntity.ok(new TokenDto(token,"Bearer"));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
