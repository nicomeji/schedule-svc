package com.gym.platform.schedule.rest.contract.common;

import lombok.Data;

@Data
public class ApiErrorDTO {
    private String message;
    private String errorCode;
}
