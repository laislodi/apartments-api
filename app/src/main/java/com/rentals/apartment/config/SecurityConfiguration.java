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

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//            // Enable Https
//            .requiresChannel((requiresChannel) -> requiresChannel.anyRequest().requiresSecure())
            .exceptionHandling(customizer -> customizer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
//            // How to configure login:
//            .formLogin(login -> login.defaultSuccessUrl("/home").failureForwardUrl("/login"))
            .logout(AbstractHttpConfigurer::disable)
//            // Another way of setting the logout
//            .logout(logout -> logout.
//                    .logoutUrl("/logout")
//                    .permitAll().logoutSuccessHandler((req, resp, authentication) -> {
//                resp.setStatus(HttpServletResponse.SC_OK);
//            }))
//            .authorizeHttpRequests((authorizeHttpRequests) ->
//                authorizeHttpRequests.requestMatchers(AntPathRequestMatcher.antMatcher("/login")).permitAll())
//                    .anyRequest().authenticated())
            .authorizeHttpRequests((requests) -> requests
                    .anyRequest().permitAll())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
