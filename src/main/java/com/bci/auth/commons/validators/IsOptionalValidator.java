package com.bci.auth.commons.validators;

import com.bci.auth.commons.decorators.StringIsOptional;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class IsOptionalValidator implements ConstraintValidator<StringIsOptional, Object> {

    private boolean notEmpty;
    private boolean notNull;
    private Class<?> type;

    @Override
    public void initialize(StringIsOptional constraintAnnotation) {
        this.notEmpty = constraintAnnotation.notEmpty();
        this.notNull = constraintAnnotation.notNull();
        this.type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (!Objects.isNull(type) && type != Object.class && !type.isInstance(value)) {
            return false;
        }

        if (notEmpty && value instanceof CharSequence) {
            return StringUtils.isNotEmpty((CharSequence) value);
        }

        return !notNull || (value != null);
    }
}


