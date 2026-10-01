package com.gym.platform.schedule.rest.contract.common;

import java.time.ZonedDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TimeRangeDTO {
    @NotNull
    @Future
    private ZonedDateTime from;

    @NotNull
    private ZonedDateTime to;
}
