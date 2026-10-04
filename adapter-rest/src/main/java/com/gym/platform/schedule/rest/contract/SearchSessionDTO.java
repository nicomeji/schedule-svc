package com.gym.platform.schedule.rest.contract;

import java.util.List;

import com.gym.platform.schedule.rest.contract.common.SearchedPageDTO;
import com.gym.platform.schedule.rest.contract.common.TimeRangeDTO;
import com.gym.platform.schedule.rest.validation.ValidTimeRange;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchSessionDTO {
    private List<@NotNull(message = "'coach_id' cannot be null") Long> coachIds;

    @Valid
    @ValidTimeRange
    private TimeRangeDTO startTime;

    @Valid
    private SearchedPageDTO page = new SearchedPageDTO();
}
