package com.genesis.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        var requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf"); // important

        // Configure security for Swagger UI and API documentation
        // Allow access to Swagger UI and API docs without authentication
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        new AntPathRequestMatcher("/swagger-ui/**"),
                        new AntPathRequestMatcher("/v3/api-docs/**"),
                        new AntPathRequestMatcher("/swagger-ui.html")
                ).permitAll()
                .anyRequest().permitAll()
        );

        // Configure CSRF protection
        // Exclude specific endpoints from CSRF protection
        // and set the CSRF token repository to use cookies
        // The CSRF token will be sent in the request header "_csrf"
        // and the CSRF token request handler will handle the CSRF token
        // for state-changing requests (POST, PUT, DELETE, etc.)
        http.csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers(
                        new AntPathRequestMatcher("/api/v1/csv/sec")
                )
                .csrfTokenRequestHandler(requestHandler)
        );

        http.addFilterAfter(new CsrfCookieFilter(), org.springframework.security.web.csrf.CsrfFilter.class);
        return http.build();
    }


}
