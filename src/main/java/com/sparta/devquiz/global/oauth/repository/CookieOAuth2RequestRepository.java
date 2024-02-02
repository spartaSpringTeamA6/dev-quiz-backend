package com.sparta.devquiz.global.oauth.repository;

import static com.sparta.devquiz.global.oauth.CookieUtil.OAUTH2_COOKIE_NAME;
import static com.sparta.devquiz.global.oauth.CookieUtil.OAUTH2_COOKIE_TIME;
import static com.sparta.devquiz.global.oauth.CookieUtil.REDIRECT_URI_COOKIE_NAME;

import com.sparta.devquiz.global.oauth.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class CookieOAuth2RequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtil.getCookie(request, OAUTH2_COOKIE_NAME)
            .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
            .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            CookieUtil.removeCookie(request, response, OAUTH2_COOKIE_NAME);
            CookieUtil.removeCookie(request, response, REDIRECT_URI_COOKIE_NAME);
            return;
        }

        CookieUtil.addCookie(response, OAUTH2_COOKIE_NAME, CookieUtil.serialize(authorizationRequest), OAUTH2_COOKIE_TIME);
        String redirectUri = request.getParameter(REDIRECT_URI_COOKIE_NAME);
        if (StringUtils.hasText(redirectUri)) {
            CookieUtil.addCookie(response, REDIRECT_URI_COOKIE_NAME, redirectUri, OAUTH2_COOKIE_TIME);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.removeCookie(request, response, OAUTH2_COOKIE_NAME);
        CookieUtil.removeCookie(request, response, REDIRECT_URI_COOKIE_NAME);
    }

}