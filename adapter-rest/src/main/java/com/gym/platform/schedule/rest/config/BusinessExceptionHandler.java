package com.gym.platform.schedule.rest.config;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gym.platform.schedule.application.exceptions.BusinessException;
import com.gym.platform.schedule.rest.contract.common.ApiErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@Order(1)
@RestControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(BusinessException.SessionOverlapException.class)
    public ResponseEntity<ApiErrorDTO> handleBusinessException(
            BusinessException.SessionOverlapException ex,
            HttpServletRequest request) {

        var response = new ApiErrorDTO();
        response.setMessage(ex.getMessage());
        response.setErrorCode(ex.getErrorCode());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
