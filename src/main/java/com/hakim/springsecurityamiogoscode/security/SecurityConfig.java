package com.hakim.springsecurityamiogoscode.security;

import com.hakim.springsecurityamiogoscode.payload.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService userDetailsService;
    @Autowired
    public SecurityConfig(CustomUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/v1/student/**")
                    .hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/api/v1/student/**")
                    .hasRole(Role.ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/api/v1/student/**")
                    .hasRole(Role.STUDENT.name())
                .anyRequest()
                    .authenticated();

        return http.build();
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        var provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}