package com.sihoily.tilboard.global.validation.validator;

import com.sihoily.tilboard.global.validation.annotation.NotWhitespace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotWhitespaceValidator
        implements ConstraintValidator<NotWhitespace, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return !value.matches(".*\\s.*");
    }
}