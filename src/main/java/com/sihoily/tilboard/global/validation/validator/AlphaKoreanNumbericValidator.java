package com.sihoily.tilboard.global.validation.validator;

import com.sihoily.tilboard.global.validation.annotation.AlphaKoreanNumberic;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AlphaKoreanNumbericValidator implements ConstraintValidator<AlphaKoreanNumberic, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value.matches("[a-zA-Z가-힣0-9]+");
    }
}