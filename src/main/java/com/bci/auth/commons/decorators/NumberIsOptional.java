package com.bci.auth.commons.decorators;

import com.bci.auth.commons.validators.NumberIsOptionalValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NumberIsOptionalValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberIsOptional {

    String message() default "Invalid number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long minSize() default 1;

    Class<?> type() default long.class;
}

