package com.gym.platform.schedule.rest.contract;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SessionDeletionDTO {
    @NotNull
    private List<@NotNull Long> participantIds = List.of();
}
