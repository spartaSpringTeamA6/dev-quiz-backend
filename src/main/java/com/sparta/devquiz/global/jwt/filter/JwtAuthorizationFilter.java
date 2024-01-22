package com.sparta.devquiz.global.jwt.filter;

import static com.sparta.devquiz.global.jwt.service.JwtService.ACCESS_TOKEN_COOKIE;

import com.sparta.devquiz.global.jwt.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = jwtService.getTokenFromRequest(request, ACCESS_TOKEN_COOKIE);
        if(StringUtils.hasText(bearerToken)) {
            Claims info = jwtService.getClaimsFromToken(jwtService.subStringToken(bearerToken));
            setAuthentication(info.getSubject());
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String oauthId) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(oauthId);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String oauthId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(oauthId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}