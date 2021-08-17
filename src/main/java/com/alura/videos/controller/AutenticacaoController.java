package com.alura.videos.controller;

import com.alura.videos.config.security.service.TokenApiService;
import com.alura.videos.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenApiService tokenService;


    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid LoginDto loginDto){
        UsernamePasswordAuthenticationToken login = loginDto.convert();

        try{
            Authentication authenticate = authManager.authenticate(login);
            String token = tokenService.gerarToken(authenticate);
            return ResponseEntity.ok().build();
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
