package com.gym.platform.schedule.rest.contract.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class SearchedPageDTO {
    @Min(0)
    private Long offset = 0L;

    @Min(10)
    @Max(100)
    private Integer size = 10;
}
