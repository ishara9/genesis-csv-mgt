package com.genesis.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//        .authorizeHttpRequests()
//        .requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll()
//        .anyRequest().authenticated()
//        .and()
//        .oauth2Login(Customizer.withDefaults())
//        .formLogin(Customizer.withDefaults());
//
//    return http.build();

    http
        .authorizeHttpRequests()
        .requestMatchers("/").permitAll()
        .anyRequest().authenticated()
        .and()
        .oauth2Login(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults());
    return http.build();
  }


}
