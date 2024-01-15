package com.sparta.devquiz.domain.user.annotation;

import com.sparta.devquiz.domain.user.validator.UserSkillValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {UserSkillValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserSkillEnum {
  String message() default "잘못된 유저 스킬입니다.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  Class<? extends java.lang.Enum<?>> enumClass();
  boolean ignoreCase() default false;
}