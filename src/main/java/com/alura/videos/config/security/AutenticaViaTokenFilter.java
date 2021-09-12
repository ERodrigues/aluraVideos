package com.alura.videos.config.security;

import com.alura.videos.config.security.service.TokenApiService;
import com.alura.videos.model.Usuario;
import com.alura.videos.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticaViaTokenFilter extends OncePerRequestFilter {

    private TokenApiService tokenService;
    private UsuarioRepository usuarioRepository;

    public AutenticaViaTokenFilter(TokenApiService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService; 
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);

        if (tokenService.eValido(token)){
            authenticateUser(token);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token) {
        Long idUsuario = tokenService.getIdUser(token);
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(autenticacao);
    }

    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")){
            return null;
        }

        return token.substring(7, token.length());
    }
}
