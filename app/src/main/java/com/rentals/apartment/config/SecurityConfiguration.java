package com.rentals.apartment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .exceptionHandling(customizer -> customizer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
//            .authorizeHttpRequests((authorizeHttpRequests) ->
//                authorizeHttpRequests.requestMatchers(AntPathRequestMatcher.antMatcher("/login")).permitAll())
//                    .anyRequest().authenticated())
            .authorizeHttpRequests((requests) -> requests
                    .anyRequest().permitAll())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
