package com.javadev.appexpiredinvoices.security;

import com.javadev.appexpiredinvoices.service.CustomerRegService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    final JwtProvider jwtProvider;
    final CustomerRegService customerRegService;

    public JwtFilter(JwtProvider jwtProvider, CustomerRegService customerRegService) {
        this.jwtProvider = jwtProvider;
        this.customerRegService = customerRegService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            String tokenWithoutBearer = token.split(" ")[1].trim();
            if (jwtProvider.validateToken(tokenWithoutBearer)) {
                String email = jwtProvider.getUsername(tokenWithoutBearer);
                if (email != null) {
                    UserDetails userDetails = customerRegService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request,response);
    }
}
