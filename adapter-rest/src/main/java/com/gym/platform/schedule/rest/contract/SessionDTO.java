package com.gym.platform.schedule.rest.contract;

import java.time.OffsetDateTime;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SessionDTO {
    private Long id;

    @NotNull
    private Long coachId;

    @NotNull
    private OffsetDateTime startTime;

    @NotNull
    private OffsetDateTime endTime;

    @NotNull
    private Integer capacity;

    @NotNull
    private String location;
}
