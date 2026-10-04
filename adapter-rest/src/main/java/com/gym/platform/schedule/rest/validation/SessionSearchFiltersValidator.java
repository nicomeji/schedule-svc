package com.gym.platform.schedule.rest.validation;

import com.gym.platform.schedule.rest.contract.SearchSessionDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SessionSearchFiltersValidator implements ConstraintValidator<ValidSessionSearchFilters, SearchSessionDTO> {
    @Override
    public boolean isValid(SearchSessionDTO dto, ConstraintValidatorContext context) {
        var isFilteringByCoach = dto.getCoachIds() != null && !dto.getCoachIds().isEmpty();
        var isFilteringByStartTime = dto.getStartTime() != null;
        return isFilteringByCoach || isFilteringByStartTime;
    }
}
