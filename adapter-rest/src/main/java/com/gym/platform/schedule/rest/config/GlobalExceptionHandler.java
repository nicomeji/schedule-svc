package com.gym.platform.schedule.rest.config;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.gym.platform.schedule.rest.contract.common.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex,
            HttpServletRequest request) {

        List<ErrorResponseDTO.FieldErrorDTO> fieldErrors = ex.getAllValidationResults()
                .stream()
                .map(this::toFieldErrorDTO)
                .toList();

        var response = new ErrorResponseDTO();
        response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setErrors(fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ErrorResponseDTO.FieldErrorDTO> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toFieldErrorDTO)
                .toList();

        var response = new ErrorResponseDTO();
        response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setErrors(fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<ErrorResponseDTO.FieldErrorDTO> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(this::toFieldErrorDTO)
                .toList();

        var response = new ErrorResponseDTO();
        response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setErrors(fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ErrorResponseDTO.FieldErrorDTO toFieldErrorDTO(ParameterValidationResult result) {
        String parameterName = result.getMethodParameter().getParameterName();
        List<String> errors = new LinkedList<>();
        var dto = new ErrorResponseDTO.FieldErrorDTO();
        result.getResolvableErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });
        dto.setField(parameterName);
        dto.setMessage(errors.toString());
        return dto;
    }

    private ErrorResponseDTO.FieldErrorDTO toFieldErrorDTO(ConstraintViolation<?> violation) {
        var dto = new ErrorResponseDTO.FieldErrorDTO();
        dto.setField(violation.getPropertyPath().toString());
        dto.setMessage(violation.getMessage());
        return dto;
    }

    private ErrorResponseDTO.FieldErrorDTO toFieldErrorDTO(FieldError error) {
        var dto = new ErrorResponseDTO.FieldErrorDTO();
        dto.setField(error.getField());
        dto.setMessage(error.getDefaultMessage());
        return dto;
    }
}
