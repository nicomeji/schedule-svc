package com.gym.platform.schedule.rest.contract;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SessionRegistrationDTO {
    @NotNull
    private Long participantId;
}
