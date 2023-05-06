package com.hakim.springsecurityamiogoscode.security;

import com.hakim.springsecurityamiogoscode.utility.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This is the gate of the api.
 * This filter stops the request and checks id request has the token.
 * If token is present is validates it otherwise lets the request go.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        if(authHeader == null || authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        // extract the token
        String token=authHeader.substring(7);
        String userEmail=jwtHelper.extractUsername(token);
    }
}
