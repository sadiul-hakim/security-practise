package com.hakim.springsecurityamiogoscode.config;

import com.hakim.springsecurityamiogoscode.payload.CustomUserDetailService;
import com.hakim.springsecurityamiogoscode.security.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
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
        String[] whiteList = {
                "/loginPage",
                "/registerPage"
        };
        http
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers(whiteList)
                    .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/student/**")
                    .hasAnyAuthority(Permission.COURSE_WRITE.getPermission())
                .requestMatchers(HttpMethod.GET, "/api/v1/student/**")
                    .hasAnyAuthority(Permission.STUDENT_READ.getPermission())
                .anyRequest()
                    .authenticated()
                .and()
                .formLogin()
                    .loginPage("/loginPage")
                    .loginProcessingUrl("/perform_login")
                    .defaultSuccessUrl("/home", true)
                .and()
                .rememberMe()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("remember-me", "JSESSIONID")
                    .logoutSuccessUrl("/loginPage");

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