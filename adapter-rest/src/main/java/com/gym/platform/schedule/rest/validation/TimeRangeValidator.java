package com.gym.platform.schedule.rest.validation;

import com.gym.platform.schedule.rest.contract.common.TimeRangeDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, TimeRangeDTO> {
    @Override
    public boolean isValid(TimeRangeDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getFrom() == null || dto.getTo() == null) {
            return true;
        }

        return dto.getFrom().isBefore(dto.getTo());
    }
}
