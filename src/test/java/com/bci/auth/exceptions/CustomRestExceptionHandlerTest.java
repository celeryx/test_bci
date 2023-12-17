package com.bci.auth.exceptions;

import com.bci.auth.commons.exceptions.CustomRestExceptionHandler;
import com.bci.auth.commons.exceptions.models.UserNotFoundException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CustomRestExceptionHandlerTest {

    @InjectMocks
    CustomRestExceptionHandler creh;

    @Mock
    WebRequest request;


    @Test
    void handleMethodArgumentNotValid_ShouldReturnBadRequest() throws Exception {
        FieldError fieldError = new FieldError("object", "field", "defaultMessage");
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult("object", "objectName");
        bindingResult.addError(fieldError);

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        Method method = CustomRestExceptionHandler.class.getDeclaredMethod(
                "handleMethodArgumentNotValid",
                MethodArgumentNotValidException.class,
                HttpHeaders.class,
                HttpStatus.class,
                WebRequest.class
        );
        method.setAccessible(true);

        ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(
                creh,
                exception,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void handleDataIntegrityViolationException_ShouldReturnConflict() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        JdbcSQLIntegrityConstraintViolationException rootCause =
                new JdbcSQLIntegrityConstraintViolationException("ddsda", null, "23505", 409, null, null);
        DataIntegrityViolationException exception = new DataIntegrityViolationException("", rootCause);

        Method method = CustomRestExceptionHandler.class.getDeclaredMethod(
                "handleDataIntegrityViolationException",
                DataIntegrityViolationException.class
        );
        method.setAccessible(true);

        ResponseEntity<Object> response = (ResponseEntity<Object>) method.invoke(
                creh,
                exception
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void handleUserNotFoundException_ShouldReturnNotFound() {
        UserNotFoundException exception = new UserNotFoundException("User not found");

        ResponseEntity<Object> response = creh.handleUserNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}

