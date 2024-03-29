package com.sparta.devquiz.domain.user.service;

import static com.sparta.devquiz.global.jwt.service.JwtService.ACCESS_COOKIE_NAME;
import static com.sparta.devquiz.global.jwt.service.JwtService.COOKIE_TIME;
import static com.sparta.devquiz.global.jwt.service.JwtService.REFRESH_COOKIE_NAME;
import static com.sparta.devquiz.global.jwt.service.JwtService.REFRESH_JWT_TIME;

import com.sparta.devquiz.global.jwt.service.JwtService;
import com.sparta.devquiz.global.redis.RedisService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtService jwtService;
  private final RedisService redisService;

  public void logout(HttpServletRequest request, HttpServletResponse response) {
    String bearerToken = jwtService.getTokenFromRequest(request, ACCESS_COOKIE_NAME);
    String accessToken = jwtService.subStringToken(bearerToken);
    Claims claims = jwtService.getClaimsFromToken(accessToken);

    String subject = claims.getSubject();
    redisService.deleteValues(subject);

    long issuedAt = claims.getExpiration().getTime();
    long curTime = new Date().getTime();
    long diff = issuedAt - curTime;
    if (diff > 0) {
      long extra = 2000;
      redisService.setBlacklist(subject, accessToken, diff + extra);
    }

    deleteCookies(request, response);
  }

  public void reissue(HttpServletRequest request, HttpServletResponse response) {
    String bearerToken = jwtService.getTokenFromRequest(request, REFRESH_COOKIE_NAME);


    if (StringUtils.hasText(bearerToken)) {

      String refreshToken = jwtService.subStringToken(bearerToken);
      Claims claims = jwtService.getClaimsFromToken(refreshToken);
      String oauthId = claims.getSubject();
      String userRole = jwtService.getUserRole(claims);

      if (redisService.hasValues(oauthId) && refreshToken.equals(redisService.getValues(oauthId))) {
        String newAccessToken = jwtService.createAccessToken(oauthId, userRole);
        String newRefreshToken = jwtService.createRefreshToken(oauthId, userRole);
        jwtService.addToCookie(response, ACCESS_COOKIE_NAME, newAccessToken, COOKIE_TIME);
        jwtService.addToCookie(response, REFRESH_COOKIE_NAME, newRefreshToken, COOKIE_TIME);
        redisService.setValues(oauthId, newRefreshToken.substring(7), REFRESH_JWT_TIME);
      }
    }
  }

  private void deleteCookies(HttpServletRequest request, HttpServletResponse response) {
    Arrays.stream(request.getCookies())
        .filter(cookie -> ACCESS_COOKIE_NAME.equals(cookie.getName()) || REFRESH_COOKIE_NAME.equals(cookie.getName()))
        .forEach(cookie -> {
          cookie.setValue("");
          cookie.setPath("/");
          cookie.setMaxAge(0);
          response.addCookie(cookie);
        });
  }
}