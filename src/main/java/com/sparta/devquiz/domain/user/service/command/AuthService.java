package com.sparta.devquiz.domain.user.service.command;

import static com.sparta.devquiz.global.jwt.service.JwtService.ACCESS_TOKEN_HEADER;
import static com.sparta.devquiz.global.jwt.service.JwtService.REFRESH_TOKEN_COOKIE;

import com.sparta.devquiz.global.jwt.service.JwtService;
import com.sparta.devquiz.global.oauth.CookieUtil;
import com.sparta.devquiz.global.redis.RedisService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtService jwtService;
  private final RedisService redisService;

  public void logout(HttpServletRequest request) {
    String accessToken = jwtService.resolveTokenFromHeader(request);

    if (StringUtils.hasText(accessToken)) {
      String subject = jwtService.getClaimsFromToken(accessToken).getSubject();
      redisService.deleteValues(subject);
    }
  }

  public void reissue(HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = jwtService.resolveTokenFromCookie(request);


    if (StringUtils.hasText(refreshToken)) {
      Claims claims = jwtService.getClaimsFromToken(refreshToken);
      String oauthId = claims.getSubject();
      String userRole = jwtService.getUserRole(claims);

      if (redisService.hasValues(oauthId) && refreshToken.equals(redisService.getValues(oauthId))) {
        String newAccessToken = jwtService.createAccessToken(oauthId, userRole);
        response.setHeader(ACCESS_TOKEN_HEADER, newAccessToken);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE, refreshToken, CookieUtil.OAUTH2_COOKIE_TIME);
      }
    }
  }
}