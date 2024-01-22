package com.sparta.devquiz.global.oauth.handler;

import static com.sparta.devquiz.global.oauth.CookieUtil.REDIRECT_URI_COOKIE_NAME;
import static com.sparta.devquiz.global.oauth.CookieUtil.getCookie;

import com.sparta.devquiz.global.oauth.repository.CookieOAuth2RequestRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final CookieOAuth2RequestRepository cookieOAuth2RequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {
      String targetUrl = getCookie(request, REDIRECT_URI_COOKIE_NAME)
                                    .map(Cookie::getValue)
                                    .orElse(("/"));

      targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                                    .queryParam("error", exception.getLocalizedMessage())
                                    .build()
                                    .toUriString();

      cookieOAuth2RequestRepository.removeAuthorizationRequestCookies(request, response);
      getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
  }