package com.gym.platform.schedule.rest.config;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gym.platform.schedule.domain.exceptions.BusinessException;
import com.gym.platform.schedule.rest.contract.common.ApiErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@Order(1)
@RestControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(BusinessException.NotFouncException.class)
    public ResponseEntity<ApiErrorDTO> handleNotFouncException(
            BusinessException.NotFouncException ex,
            HttpServletRequest request) {

        var response = new ApiErrorDTO();
        response.setMessage(ex.getMessage());
        response.setErrorCode(ex.getErrorCode());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({
            BusinessException.SessionOverlapException.class,
            BusinessException.SessoinCapacityExceededException.class,
            BusinessException.DuplicateSessionRegistrationException.class })
    public ResponseEntity<ApiErrorDTO> handleConflictException(
            BusinessException ex,
            HttpServletRequest request) {

        var response = new ApiErrorDTO();
        response.setMessage(ex.getMessage());
        response.setErrorCode(ex.getErrorCode());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(BusinessException.NotEmpstySessionException.class)
    public ResponseEntity<ApiErrorDTO> handleNotEmptyException(
            BusinessException ex,
            HttpServletRequest request) {

        var response = new ApiErrorDTO();
        response.setMessage(ex.getMessage());
        response.setErrorCode(ex.getErrorCode());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
