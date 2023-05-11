package com.hakim.springsecurityamiogoscode.security;

import com.hakim.springsecurityamiogoscode.payload.CustomUserDetailService;
import com.hakim.springsecurityamiogoscode.utility.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    private final CustomUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        if(authHeader == null || authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        // Extract the token
        String token=authHeader.substring(7);

        // Extract the username from the token
        String userEmail=jwtHelper.extractUsername(token);

        // If the token contains username and user is not authenticated, program will enter the if block
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // todo: there is a case when user is authenticated

            // Find the userDetails using the username
            UserDetails userDetails = userDetailService.loadUserByUsername(userEmail);
            // todo: Here we are only validating username and token but not password.
            // todo: There is not problem. When the filter passes we call authentication.authenticate()
            // todo: which will validate the password;

            // If the token is valid, We need to update out SecurityContextHolder
            if(jwtHelper.isTokenValid(token,userDetails)){
                // todo: There is a case when token is not valid

                // Create UsernamePasswordAuthenticationToken
                var authToken=new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Give UsernamePasswordAuthenticationToken more details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set UsernamePasswordAuthenticationToken in SecurityContextHolder.
                // SecurityContextHolder keeps Authentication information
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}
