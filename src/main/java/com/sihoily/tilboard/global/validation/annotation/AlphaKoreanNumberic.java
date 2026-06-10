package com.sihoily.tilboard.global.validation.annotation;

import com.sihoily.tilboard.global.validation.validator.AlphaKoreanNumbericValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AlphaKoreanNumbericValidator.class)
public @interface AlphaKoreanNumberic {
    String message() default "영어·한글·숫자만 허용됩니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
