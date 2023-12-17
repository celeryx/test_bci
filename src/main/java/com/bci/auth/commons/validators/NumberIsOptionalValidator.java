package com.bci.auth.commons.validators;

import com.bci.auth.commons.decorators.NumberIsOptional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberIsOptionalValidator implements ConstraintValidator<NumberIsOptional, Object> {

    private long minSize;
    private Class<?> type;

    @Override
    public void initialize(NumberIsOptional constraintAnnotation) {
        this.minSize = constraintAnnotation.minSize();
        this.type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            String numberString = value.toString();
            if (numberString.length() < minSize) {
                return false;
            }
            convertToNumber(numberString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Number convertToNumber(String value) throws NumberFormatException {
        if (type.equals(long.class) || type.equals(Long.class)) {
            return Long.parseLong(value);
        } else if (type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(float.class) || type.equals(Float.class)) {
            return Float.parseFloat(value);
        } else if (type.equals(double.class) || type.equals(Double.class)) {
            return Double.parseDouble(value);
        }

        throw new NumberFormatException("Unsupported number type: " + type.getName());
    }
}
