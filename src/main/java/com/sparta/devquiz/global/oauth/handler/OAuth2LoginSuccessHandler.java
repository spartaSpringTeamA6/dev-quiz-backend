package com.sparta.devquiz.global.oauth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.devquiz.domain.user.response.UserResponseCode;
import com.sparta.devquiz.global.jwt.JwtUtil;
import com.sparta.devquiz.global.response.CommonResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;
  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

    String oauthId = oAuth2User.getName();

    ArrayList<? extends GrantedAuthority> authorities = new ArrayList<>(oAuth2User.getAuthorities());

    String accessToken = jwtUtil.createAccessToken(oauthId, authorities.get(0).getAuthority());

    response.setHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.setStatus(UserResponseCode.LOGIN.getHttpStatus().value());

    CommonResponseDto result = CommonResponseDto.of(UserResponseCode.LOGIN);
    response.getWriter().write(objectMapper.writeValueAsString(result));
  }
}