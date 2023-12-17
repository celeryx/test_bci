package com.bci.auth.validators;

import com.bci.auth.commons.decorators.ListWithNullObjects;
import com.bci.auth.commons.validators.ListWithNullObjectsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Payload;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListWithNullObjectsValidatorTest {

    private ListWithNullObjectsValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ListWithNullObjectsValidator();
    }

    @Test
    void isValid_ShouldReturnTrue_WhenAllowNullIsTrue() {
        validator.initialize(new ListWithNullObjects() {
            @Override
            public Class<ListWithNullObjects> annotationType() {
                return ListWithNullObjects.class;
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public boolean allowNull() {
                return true;
            }
        });

        assertTrue(validator.isValid(null, null));
        assertTrue(validator.isValid(Collections.singletonList(null), null));
    }

    @Test
    void isValid_ShouldReturnTrue_WhenListIsValid() {
        validator.initialize(new ListWithNullObjects() {
            @Override
            public Class<ListWithNullObjects> annotationType() {
                return ListWithNullObjects.class;
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public boolean allowNull() {
                return false;
            }
        });

        assertTrue(validator.isValid(Arrays.asList("item1", "item2"), null));
    }

    @Test
    void isValid_ShouldReturnFalse_WhenListContainsNull() {
        validator.initialize(new ListWithNullObjects() {
            @Override
            public Class<ListWithNullObjects> annotationType() {
                return ListWithNullObjects.class;
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public boolean allowNull() {
                return false;
            }
        });

        assertFalse(validator.isValid(Arrays.asList("item1", null, "item2"), null));
    }
}

