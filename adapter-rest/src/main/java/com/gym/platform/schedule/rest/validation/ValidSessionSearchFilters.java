package com.gym.platform.schedule.rest.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target ({ ElementType.TYPE, ElementType.PARAMETER })
@Retention (RetentionPolicy.RUNTIME)
@Constraint (validatedBy = SessionSearchFiltersValidator.class)
public @interface ValidSessionSearchFilters {
    String message() default "at least one filter must be present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
