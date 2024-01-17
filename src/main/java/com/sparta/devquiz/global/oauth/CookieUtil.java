package com.sparta.devquiz.global.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Optional;
import org.springframework.util.SerializationUtils;

public class CookieUtil {

  public static final String OAUTH2_COOKIE_NAME = "access_auth";
  public static final String REDIRECT_URI_COOKIE_NAME = "redirect_uri";
  public static final int OAUTH2_COOKIE_TIME = 1 * 60 * 60;

  public static Optional<Cookie> getCookie(HttpServletRequest request, String name){
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          return Optional.of(cookie);
        }
      }
    }

    return Optional.empty();
  }

  public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setMaxAge(maxAge);
    cookie.setHttpOnly(true);
    response.addCookie(cookie);
  }

  public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie: cookies) {
        if (cookie.getName().equals(name)) {
          cookie.setValue("");
          cookie.setPath("/");
          cookie.setMaxAge(0);
          response.addCookie(cookie);
        }
      }
    }
  }

  public static String serialize(Object object) {
    return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
  }

  public static <T> T deserialize(Cookie cookie, Class<T> clazz) {
    return clazz.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
  }
}