package com.sparta.devquiz.global.jwt.filter;

import static com.sparta.devquiz.global.jwt.service.JwtService.ACCESS_COOKIE_NAME;

import com.sparta.devquiz.global.jwt.service.JwtService;
import com.sparta.devquiz.global.redis.RedisService;
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
    private final RedisService redisService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = jwtService.getTokenFromRequest(request, ACCESS_COOKIE_NAME);
        if (StringUtils.hasText(bearerToken)) {
            String accessToken = jwtService.subStringToken(bearerToken);
            String subject = jwtService.getClaimsFromToken(accessToken).getSubject();

            if (!redisService.hasBlacklist(subject, accessToken)) {
                setAuthentication(subject);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String subject) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(subject);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String subject) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}