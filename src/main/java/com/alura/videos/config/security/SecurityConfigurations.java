package com.alura.videos.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* Parametriza quais URLs do projeto serão publicas */
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/categorias").permitAll()
                .anyRequest().authenticated()
                .and().formLogin();
    }
}
