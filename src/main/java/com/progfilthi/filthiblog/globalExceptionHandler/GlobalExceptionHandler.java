package com.progfilthi.filthiblog.globalExceptionHandler;

import com.progfilthi.filthiblog.models.dto.api.ApiResponseError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //For 400 status codes - validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMsg = error.getDefaultMessage();

            errors.put(fieldName, errorMsg);
        });

        String message = "Validation failed: " + errors.keySet();

        ApiResponseError apiResponseError = new ApiResponseError(
                message,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()

        );

        return new ResponseEntity<>(apiResponseError, HttpStatus.BAD_REQUEST);
    }

    //404 - not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseError> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request){


        ApiResponseError apiResponseError = new ApiResponseError(
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()

        );

        return new ResponseEntity<>(apiResponseError, HttpStatus.NOT_FOUND);
    }

    //409 - already exists
    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiResponseError> handleResourceConflictException(ResourceConflictException ex, HttpServletRequest request){


        ApiResponseError apiResponseError = new ApiResponseError(
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()

        );

        return new ResponseEntity<>(apiResponseError, HttpStatus.CONFLICT);
    }

    //500 - internal server errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseError> handleGenericException(Exception ex, HttpServletRequest request){


        ApiResponseError apiResponseError = new ApiResponseError(
                ex.getMessage(),
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()

        );

        return new ResponseEntity<>(apiResponseError, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
