package com.sihoily.tilboard.global.validation.validator;

import com.sihoily.tilboard.global.validation.annotation.AlphaNumberic;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AlphaNumbericValidator implements ConstraintValidator<AlphaNumberic, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value.matches("[a-zA-Z0-9]+");
    }
}
