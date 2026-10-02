package com.gym.platform.schedule.rest.contract.common;

import java.util.List;

import lombok.Data;

@Data 
public class ErrorResponseDTO{
    private int status;
    private String error;
    private List<FieldErrorDTO> errors;

    @Data 
    public static class FieldErrorDTO{
        private String field;
        private String message;
    }
}
