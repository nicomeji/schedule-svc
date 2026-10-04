package com.gym.platform.schedule.domain.model;

import java.util.List;

import com.gym.platform.schedule.domain.common.SearchedPage;
import com.gym.platform.schedule.domain.common.TimeRange;

import lombok.Data;
import lombok.NonNull;

@Data
public class SessionFilters {
    private final List<Long> coachIds;

    private final TimeRange startTime;

    @NonNull
    private final SearchedPage page;
}
