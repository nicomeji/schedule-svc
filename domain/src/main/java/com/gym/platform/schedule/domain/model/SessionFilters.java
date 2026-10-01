package com.gym.platform.schedule.domain.model;

import java.util.List;

import com.gym.platform.schedule.domain.common.SearchedPage;
import com.gym.platform.schedule.domain.common.TimeRange;

public record SessionFilters(
        List<Long> coachIds,
        TimeRange startTime,
        SearchedPage page) {
}
