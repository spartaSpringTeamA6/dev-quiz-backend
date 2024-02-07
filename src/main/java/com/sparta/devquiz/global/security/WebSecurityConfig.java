package com.sparta.devquiz.global.security;

import com.sparta.devquiz.global.jwt.filter.JwtAuthorizationFilter;
import com.sparta.devquiz.global.jwt.filter.JwtExceptionHandlerFilter;
import com.sparta.devquiz.global.jwt.service.JwtService;
import com.sparta.devquiz.global.oauth.handler.OAuth2LoginFailureHandler;
import com.sparta.devquiz.global.oauth.handler.OAuth2LoginSuccessHandler;
import com.sparta.devquiz.global.oauth.repository.CookieOAuth2RequestRepository;
import com.sparta.devquiz.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtService jwtService;
  private final RedisService redisService;
  private final OAuth2UserService oAuth2UserService;
  private final UserDetailsService userDetailsService;
  private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
  private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
  private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
  private final CookieOAuth2RequestRepository cookieOAuth2RequestRepository;

  @Bean
  public JwtAuthorizationFilter jwtAuthorizationFilter() {
    return new JwtAuthorizationFilter(jwtService, redisService, userDetailsService);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.formLogin(AbstractHttpConfigurer::disable);

    http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.authorizeHttpRequests(authReq -> authReq
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
        .requestMatchers("/api/quizzes/{quizId}/boards", "/api/boards/{boardId}", "/api/boards/{boardId}/comments").permitAll()
        .requestMatchers("/api/quizzes", "/api/quizzes/{quizId}", "/api/quizzes/{quizId}/pass").permitAll()
        .requestMatchers("/api/categories").permitAll()
        .requestMatchers("/api/auth/reissue").permitAll()
        .anyRequest().authenticated()
    );

    http.oauth2Login(
        login -> login
            .loginPage("https://devquiz.pro/login")
            .authorizationEndpoint(endPoint -> endPoint.authorizationRequestRepository(cookieOAuth2RequestRepository))
            .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
            .successHandler(oAuth2LoginSuccessHandler)
            .failureHandler(oAuth2LoginFailureHandler)
    );

    http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(jwtExceptionHandlerFilter, JwtAuthorizationFilter.class);

    return http.build();
  }
}