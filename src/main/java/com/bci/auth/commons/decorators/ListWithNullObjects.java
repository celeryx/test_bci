package com.bci.auth.commons.decorators;

import com.bci.auth.commons.validators.ListWithNullObjectsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ListWithNullObjectsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListWithNullObjects {

    String message() default "List cannot contain null value as objects.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean allowNull() default false;
}
