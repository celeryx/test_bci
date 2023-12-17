package com.bci.auth.commons.exceptions;

import com.bci.auth.commons.exceptions.models.ApiError;
import com.bci.auth.commons.exceptions.models.UserNotFoundException;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String SQL_STATE_UNIQUE_CONSTRAINT_VIOLATION = "23505";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(err -> errors.add(formatFieldError(err)));

        ex.getBindingResult()
                .getGlobalErrors()
                .forEach(err -> errors.add(formatGlobalError(err)));

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                "Validation failed. Please check the following fields.",
                errors);

        return handleExceptionInternal(
                ex,
                apiError,
                headers,
                apiError.getStatus(),
                request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof JdbcSQLIntegrityConstraintViolationException) {
            JdbcSQLIntegrityConstraintViolationException sqlException = (JdbcSQLIntegrityConstraintViolationException) rootCause;

            if (SQL_STATE_UNIQUE_CONSTRAINT_VIOLATION.equals(sqlException.getSQLState())) {
                String message = "The provided email is already registered.";
                ApiError apiError = new ApiError(HttpStatus.CONFLICT, message);
                return ResponseEntity.status(apiError.getStatus()).body(apiError);
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }

    private String formatGlobalError(ObjectError globalError) {
        return globalError.getObjectName() + ": " + globalError.getDefaultMessage();
    }

}
