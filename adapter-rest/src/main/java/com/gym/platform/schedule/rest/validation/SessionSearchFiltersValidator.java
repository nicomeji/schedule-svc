package com.gym.platform.schedule.rest.validation;

import com.gym.platform.schedule.rest.contract.SessionDTO;
import com.gym.platform.schedule.rest.contract.SessionDTO.SearchSessionDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SessionSearchFiltersValidator  implements ConstraintValidator<ValidSessionSearchFilters, SessionDTO.SearchSessionDTO> {
    @Override
    public boolean isValid(SearchSessionDTO dto, ConstraintValidatorContext context) {
        return dto.getCoachIds() != null || dto.getStartTime() != null;
    }
}
