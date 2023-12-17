package com.bci.auth.commons.validators;

import com.bci.auth.commons.decorators.ListWithNullObjects;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

@Component
public class ListWithNullObjectsValidator implements ConstraintValidator<ListWithNullObjects, List<?>> {

    private boolean allowNull;

    @Override
    public void initialize(ListWithNullObjects constraintAnnotation) {
        this.allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        if (allowNull || value == null) {
            return true;
        }

        return value.stream().noneMatch(Objects::isNull);
    }
}
