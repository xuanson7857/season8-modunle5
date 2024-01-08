package com.ra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    private UserDetailService userDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authenticationProvider(authenticationProvider())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/home/**", "/auth/**", "/403Page/**")
                                .permitAll()
                                .requestMatchers("/admin","/admin/**")
                                .hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                ).formLogin(
                        (login) -> login
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/auth/login")
                                .usernameParameter("userName")
                                .passwordParameter("password")
                                .failureUrl("/auth/login?err")
                                .defaultSuccessUrl("/admin")
                ).exceptionHandling(exception ->{
                    exception.accessDeniedPage("/403Page");
                })
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
