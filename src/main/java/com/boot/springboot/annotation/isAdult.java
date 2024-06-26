package com.boot.springboot.annotation;

import com.boot.springboot.annotation.validator.AdultValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(
        validatedBy = {AdultValidator.class}
)
public @interface isAdult {
    String message() default "age must be more than 18!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
