package com.sparta.devquiz.global.jwt.service;

import com.sparta.devquiz.global.jwt.dto.TokenSet;
import com.sparta.devquiz.global.jwt.exception.JwtCustomException;
import com.sparta.devquiz.global.jwt.exception.JwtExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JwtService {

  public static final String BEARER_PREFIX = "Bearer ";
  public static final String AUTHORIZATION_KEY = "auth";
  public static final String ACCESS_TOKEN_HEADER = "Authorization";
  public static final long ACCESS_TOKEN_TIME  = 1 * 60 * 60 * 1000L;
  public static final String REFRESH_TOKEN_COOKIE = "refresh_token";
  public static final long REFRESH_TOKEN_TIME  = 7 * 24 * 60 * 60 * 1000L;

  @Value("${jwt.secret.key}")
  private String secretKey;

  private Key key;

  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  public String resolveToken(HttpServletRequest request) {
    String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
    if (StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_PREFIX)) {
      return accessToken.substring(7);
    }
    return null;
  }

  public String resolveTokenFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader(ACCESS_TOKEN_HEADER);
    return subStringToken(bearerToken);
  }

  public String resolveTokenFromCookie(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    String bearerToken = null;
    if(cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(REFRESH_TOKEN_COOKIE)) {
          bearerToken = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
        }
      }
    }
    return subStringToken(bearerToken);
  }

  public TokenSet createTokenSet(String oauthId, String role) {
    return TokenSet.builder()
        .accessToken(createAccessToken(oauthId, role))
        .refreshToken(createRefreshToken(oauthId, role))
        .build();
  }

  public String createAccessToken(String oauthId, String role) {
    return this.createToken(oauthId, role, ACCESS_TOKEN_TIME);
  }

  public String createRefreshToken(String oauthId, String role) {
    return this.createToken(oauthId, role, REFRESH_TOKEN_TIME);
  }

  public String getUserRole(Claims claims) {
    return claims.get(AUTHORIZATION_KEY, String.class);
  }

  public Claims getClaimsFromToken(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      throw new JwtCustomException(JwtExceptionCode.EXPIRED_TOKEN);
    } catch (SecurityException | MalformedJwtException | SignatureException e) {
      throw new JwtCustomException(JwtExceptionCode.TOKEN_EXCEPTION);
    } catch (UnsupportedJwtException e) {
      throw new JwtCustomException(JwtExceptionCode.UNSUPPORTED_TOKEN);
    } catch (IllegalArgumentException e) {
      throw new JwtCustomException(JwtExceptionCode.ILLEGAL_TOKEN);
    }
  }

  private static String subStringToken(String bearerToken) {
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    throw new JwtCustomException(JwtExceptionCode.BAD_REQUEST_TOKEN);
  }

  private String createToken(String oauthId, String role, long expiration) {
    Date date = new Date();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(oauthId)
            .claim(AUTHORIZATION_KEY, role)
            .setExpiration(new Date(date.getTime() + expiration))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
  }
}