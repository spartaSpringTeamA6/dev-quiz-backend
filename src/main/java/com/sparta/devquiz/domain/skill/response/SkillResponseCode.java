package com.sparta.devquiz.domain.skill.response;

import com.sparta.devquiz.domain.user.response.UserResponseCode;
import com.sparta.devquiz.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SkillResponseCode implements ResponseCode {
  // OK 200
  OK(HttpStatus.OK, "REQUEST SUCCESS");

  private final HttpStatus httpStatus;
  private final String message;

  public HttpStatus getHttpStatus(UserResponseCode responseCode) {
    return responseCode.getHttpStatus();
  }

  public String getMessage(UserResponseCode responseCode){
    return responseCode.getMessage();
  }
}
