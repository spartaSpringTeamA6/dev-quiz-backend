package com.sparta.devquiz.global.oauth.handler;

import static com.sparta.devquiz.global.jwt.service.JwtService.ACCESS_TOKEN_COOKIE;
import static com.sparta.devquiz.global.jwt.service.JwtService.ACCESS_TOKEN_TIME;
import static com.sparta.devquiz.global.jwt.service.JwtService.REFRESH_TOKEN_COOKIE;
import static com.sparta.devquiz.global.jwt.service.JwtService.REFRESH_TOKEN_TIME;
import static com.sparta.devquiz.global.oauth.CookieUtil.REDIRECT_URI_COOKIE_NAME;

import com.sparta.devquiz.global.jwt.dto.TokenSet;
import com.sparta.devquiz.global.jwt.service.JwtService;
import com.sparta.devquiz.global.oauth.CookieUtil;
import com.sparta.devquiz.global.oauth.repository.CookieOAuth2RequestRepository;
import com.sparta.devquiz.global.redis.RedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtService jwtService;
  private final RedisService redisService;
  private final CookieOAuth2RequestRepository cookieOAuth2RequestRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    String targetUrl = determineTargetUrl(request, response, authentication);
    TokenSet tokenSet = createTokenSet(authentication);

    jwtService.addToCookie(response, ACCESS_TOKEN_COOKIE, tokenSet.getAccessToken(), ACCESS_TOKEN_TIME);
    jwtService.addToCookie(response, REFRESH_TOKEN_COOKIE, tokenSet.getRefreshToken(), REFRESH_TOKEN_TIME);

    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }

  @Override
  protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_COOKIE_NAME).map(Cookie::getValue);
    clearAuthenticationAttributes(request, response);
    return redirectUri.orElse(getDefaultTargetUrl());
  }

  private TokenSet createTokenSet(Authentication authentication) {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

    String oauthId = oAuth2User.getName();

    String authority = new ArrayList<>(oAuth2User.getAuthorities()).get(0).getAuthority();

    TokenSet tokenSet = jwtService.createTokenSet(oauthId, authority);

    redisService.setValues(oAuth2User.getName(), tokenSet.getRefreshToken().substring(7), REFRESH_TOKEN_TIME);

    return tokenSet;
  }

  protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
    cookieOAuth2RequestRepository.removeAuthorizationRequestCookies(request, response);
  }
}