package com.gym.platform.schedule.rest.contract.common;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TimeRangeDTO {
    @NotNull
    @Future
    private OffsetDateTime from;

    private OffsetDateTime to;
}
