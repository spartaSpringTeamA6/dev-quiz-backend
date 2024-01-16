package com.sparta.devquiz.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.devquiz.global.exception.CustomException;
import com.sparta.devquiz.global.response.CommonResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (CustomException e) {
      response.setStatus(e.getHttpStatus().value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());

      CommonResponseDto result = new CommonResponseDto(e.getHttpStatus().value(), e.getMessage());
      response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
  }

}