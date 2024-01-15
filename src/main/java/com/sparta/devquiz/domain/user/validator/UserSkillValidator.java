package com.sparta.devquiz.domain.user.validator;

import com.sparta.devquiz.domain.user.annotation.UserSkillEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UserSkillValidator implements ConstraintValidator<UserSkillEnum, List<String>> {

  private UserSkillEnum annotation;

  @Override
  public void initialize(UserSkillEnum constraintAnnotation) {
    this.annotation = constraintAnnotation;
  }


  @Override
  public boolean isValid(List<String> values, ConstraintValidatorContext context) {
    //유저 스킬이 아예 없을 수 있음
    if (values.isEmpty()) return true;

    boolean flag = false;
    Object[] enumValues = this.annotation.enumClass().getEnumConstants();

    if (!values.isEmpty() && enumValues != null) {
      List<String> enumList = Arrays.stream(enumValues)
                                    .map(Object::toString)
                                    .toList();
      flag = enumList.containsAll(values);
    }
    return flag;
  }
}