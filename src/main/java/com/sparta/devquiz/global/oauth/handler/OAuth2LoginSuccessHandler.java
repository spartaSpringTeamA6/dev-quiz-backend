package com.sparta.devquiz.global.oauth.handler;

import static com.sparta.devquiz.global.oauth.CookieUtil.REDIRECT_URI_COOKIE_NAME;
import static com.sparta.devquiz.global.jwt.service.JwtService.REFRESH_TOKEN_TIME;

import com.sparta.devquiz.global.oauth.CookieUtil;
import com.sparta.devquiz.global.jwt.service.JwtService;
import com.sparta.devquiz.global.jwt.dto.TokenSet;
import com.sparta.devquiz.global.oauth.repository.CookieOAuth2RequestRepository;
import com.sparta.devquiz.global.redis.RedisService;
import jakarta.servlet.ServletException;
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
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private static final String ACCESS_TOKEN_PARAM = "accessToken";
  private static final String REFRESH_TOKEN_PARAM = "refreshToken";

  private final JwtService jwtService;
  private final RedisService redisService;
  private final CookieOAuth2RequestRepository cookieOAuth2RequestRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    String targetUrl = determineTargetUrl(request, response, authentication);
    TokenSet tokenSet = createTokenSet(authentication);
    getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(targetUrl, tokenSet));
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

  private String getRedirectUrl(String targetUrl, TokenSet tokenSet) {
    return UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam(ACCESS_TOKEN_PARAM, tokenSet.getAccessToken())
        .queryParam(REFRESH_TOKEN_PARAM, tokenSet.getRefreshToken())
        .build()
        .toUriString();
  }

  protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
    cookieOAuth2RequestRepository.removeAuthorizationRequestCookies(request, response);
  }
}