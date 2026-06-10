package com.sihoily.tilboard.global.validation.annotation;

import com.sihoily.tilboard.global.validation.validator.NotWhitespaceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotWhitespaceValidator.class)
public @interface NotWhitespace {
    String message() default "공백 문자를 포함할 수 없습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
