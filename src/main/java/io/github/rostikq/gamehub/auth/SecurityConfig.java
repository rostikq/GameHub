package io.github.rostikq.gamehub.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http, JwtAuthentificationFilter jwtAuthentificationFilter
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()
                        .requestMatchers("/", "/index.html").permitAll()
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/api/comment/**").permitAll()
                        .requestMatchers("/api/test/**")
                        .permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/games/getall").permitAll()
                        .requestMatchers("/api/games/admin/**").hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .addFilterBefore(jwtAuthentificationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
