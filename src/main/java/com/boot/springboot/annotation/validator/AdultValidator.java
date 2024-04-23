package com.boot.springboot.annotation.validator;

import com.boot.springboot.annotation.isAdult;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AdultValidator implements ConstraintValidator<isAdult, Integer> {

    @Override
    public void initialize(isAdult constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value > 18;
    }
}
