package com.security.test.securityconftest.config.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Resource
    private JWTService jwtService;
    @Resource
    private SpringUserDetails springUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(v -> v.startsWith("Bearer "))
                .ifPresent(v -> {
                    final String username = jwtService.retrieveSubject(v.substring(7));
                    System.err.println(v);
                    System.err.println(v.substring(7));
                    final UserDetails userDetails = springUserDetails.loadUserByUsername(username);
                    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    System.err.println("JWT Filter有執行完畢");
                });

        filterChain.doFilter(request, response);
    }
}
