package com.bci.auth.commons.decorators;

import com.bci.auth.commons.validators.IsOptionalValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsOptionalValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringIsOptional {

    boolean notEmpty() default false;

    boolean notNull() default false;

    Class<?> type() default Object.class;

    String message() default "Invalid value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

