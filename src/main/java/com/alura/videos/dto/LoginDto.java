package com.alura.videos.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginDto {

    public String login;
    public String senha;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(login, senha);
    }
}
