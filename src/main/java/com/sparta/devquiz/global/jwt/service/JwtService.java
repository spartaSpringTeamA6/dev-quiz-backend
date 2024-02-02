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
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  public static final String BEARER_PREFIX = "Bearer ";
  public static final String AUTHORIZATION_KEY = "auth";
  public static final String ACCESS_COOKIE_NAME = "access_token";
  public static final int ACCESS_JWT_TIME  = 1 * 60 * 60 * 1000;
  public static final String REFRESH_COOKIE_NAME = "refresh_token";
  public static final int REFRESH_JWT_TIME = 7 * 24 * 60 * 60 * 1000;
  public static final int COOKIE_TIME = 7 * 24 * 60 * 60;

  @Value("${jwt.secret.key}")
  private String secretKey;

  private Key key;

  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  public void addToCookie(HttpServletResponse res, String name, String value, int maxAge) {
    value = URLEncoder.encode(value, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setSecure(true);
    cookie.setMaxAge(maxAge);
    if (name.equals(REFRESH_COOKIE_NAME)) {
      cookie.setHttpOnly(true);
    }
    res.addCookie(cookie);
  }

  public String getTokenFromRequest(HttpServletRequest req, String name) {
    Cookie[] cookies = req.getCookies();
    if(cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
        }
      }
    }
    return null;
  }

  public String subStringToken(String bearerToken) {
    if (bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    throw new JwtCustomException(JwtExceptionCode.BAD_REQUEST_TOKEN);
  }

  public TokenSet createTokenSet(String oauthId, String role) {
    return TokenSet.builder()
        .accessToken(createAccessToken(oauthId, role))
        .refreshToken(createRefreshToken(oauthId, role))
        .build();
  }

  public String createAccessToken(String oauthId, String role) {
    return this.createToken(oauthId, role, ACCESS_JWT_TIME);
  }

  public String createRefreshToken(String oauthId, String role) {
    return this.createToken(oauthId, role, REFRESH_JWT_TIME);
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

  private String createToken(String oauthId, String role, int expiration) {
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